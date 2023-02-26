package my.wiki.crawler;

import my.wiki.common.KafkaProperties;
import my.wiki.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageProducer.class);
    private final KafkaTemplate<String, Page> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public PageProducer(KafkaTemplate<String, Page> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    public void producePage(Page page) {
        LOGGER.info("Publishing page: {}", page);
        kafkaTemplate.send(kafkaProperties.getGraphTopic(), page);
    }
}
