package my.wiki.graph;

import my.wiki.common.Page;
import my.wiki.graph.repository.GraphNode;
import my.wiki.graph.repository.GraphRepository;
import my.wiki.graph.repository.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static my.wiki.common.Util.hash;

@Service
@Transactional
public class PageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageConsumer.class);
    private final GraphRepository repository;

    public PageConsumer(GraphRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${wiki-config.graph-topic}", groupId = "${wiki-config.graph-group}", containerFactory = "pageKafkaListenerContainerFactory")
    public void onReceiveNewPage(Page newPage) {
        LOGGER.debug("Received new page {}", newPage.getUri());
        Optional<GraphNode> foundPage = repository.findById(hash(newPage.getUri().toString()));
        if (foundPage.isPresent()
                && foundPage.get().getVisitedAt() != null
                && foundPage.get().getLastChange() != null
                && (foundPage.get().getVisitedAt().isAfter(newPage.getVisitedDate())
                || foundPage.get().getLastChange().isEqual(newPage.getLastChangeDate()))) {
            LOGGER.debug("Page already exists in graph, ignoring");
            return;
        }
        List<GraphNode> newNodes = getNewNodes(newPage.getUris());
        LOGGER.debug("Found {} new nodes to connect", newNodes.size());
        repository.saveAll(newNodes);
        LOGGER.debug("Saved {} new nodes to connect", newNodes.size());
        GraphNode newNode = ModelMapper.map(newPage);
        repository.save(newNode);
        LOGGER.debug("Saved new node {}, last change {}", newNode.getUrl(), newNode.getLastChange());
    }

    private List<GraphNode> getNewNodes(List<URI> urls) {
        List<GraphNode> oldNodes = new ArrayList<>();
        repository.findAllById(urls.stream().map(u -> hash(u.toString())).collect(Collectors.toSet())).forEach(oldNodes::add);
        return urls.stream().map(ModelMapper::map).filter(n -> !oldNodes.contains(n)).collect(Collectors.toList());
    }
}
