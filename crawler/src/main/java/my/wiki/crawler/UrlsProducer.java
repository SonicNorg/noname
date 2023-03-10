package my.wiki.crawler;

import my.wiki.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class UrlsProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsProducer.class);
    private final KafkaTemplate<String, URI> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public UrlsProducer(KafkaTemplate<String, URI> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    public void produceUrls(List<URI> uris) {
        LOGGER.info("Publishing uris: {}", uris);
        uris.forEach(uri -> kafkaTemplate.send(kafkaProperties.getRawUrlsTopic(), uri));
    }
}
