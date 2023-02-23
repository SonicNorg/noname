package my.wiki.crawler;

import my.wiki.common.Page;
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
        Page page = extractor.extractLinks(url);
        //TODO отправить page в очередь графопостроителя
        producer.produceUrls(page.getUrls());
    }
}
