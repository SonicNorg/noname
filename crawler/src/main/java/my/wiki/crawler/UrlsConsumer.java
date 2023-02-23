package my.wiki.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class UrlsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsConsumer.class);
    private final UrlsProducer producer;
    private final LinksExtractor extractor;

    public UrlsConsumer(UrlsProducer producer, LinksExtractor extractor) {
        this.producer = producer;
        this.extractor = extractor;
    }

    @KafkaListener(topics = "${wiki-config.unique-urls-topic}", groupId = "${wiki-config.crawlers-group}")
    public void onReceiveNewUrl(URL url) {
        LOGGER.info("Received URL {}", url);
        producer.produceUrls(extractor.extractLinks(url));
    }
}
