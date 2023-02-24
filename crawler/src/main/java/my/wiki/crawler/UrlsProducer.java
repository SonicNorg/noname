package my.wiki.crawler;

import my.wiki.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class UrlsProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsProducer.class);
    private final KafkaTemplate<String, URI> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    //TODO нельзя это оставлять: при запуске нескольких экземпляров это сработает у каждого. Это только для отладки.
    private final URI startUri = new URI("https://ru.wikipedia.org/wiki/Википедия:Статья");

    public UrlsProducer(KafkaTemplate<String, URI> kafkaTemplate, KafkaProperties kafkaProperties) throws URISyntaxException {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    //TODO нельзя это оставлять: при запуске нескольких экземпляров это сработает у каждого. Это только для отладки.
    @PostConstruct
    public void firstUrl() {
        LOGGER.info("Auto produce first url {}", startUri);
        kafkaTemplate.send(kafkaProperties.getUniqueUrlsTopic(), startUri);
    }

    public void produceUrls(List<URI> uris) {
        LOGGER.info("Publishing uris: {}", uris);
        uris.forEach(uri -> kafkaTemplate.send(kafkaProperties.getRawUrlsTopic(), uri));
    }
}
