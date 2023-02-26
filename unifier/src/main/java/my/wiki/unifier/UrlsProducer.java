package my.wiki.unifier;

import my.wiki.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlsProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsProducer.class);
    private final KafkaTemplate<String, URI> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public UrlsProducer(KafkaTemplate<String, URI> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    @PostConstruct
    public void initial() throws URISyntaxException {
        produceUrl(new URI("https://ru.wikipedia.org/wiki/Большая_семёрка"));
    }

    public void produceUrl(URI uri) {
        LOGGER.info("Publishing uri: {}", uri);
        kafkaTemplate.send(kafkaProperties.getUniqueUrlsTopic(), uri);
    }
}
