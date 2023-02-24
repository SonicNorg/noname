package my.wiki.crawler;

import my.wiki.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsConsumer.class);
    private final UrlsProducer urlsProducer;
    private final PageProducer pageProducer;
    private final LinksExtractor extractor;

    public UrlsConsumer(UrlsProducer urlsProducer, PageProducer pageProducer, LinksExtractor extractor) {
        this.urlsProducer = urlsProducer;
        this.pageProducer = pageProducer;
        this.extractor = extractor;
    }

    @KafkaListener(topics = "${wiki-config.unique-urls-topic}", groupId = "${wiki-config.crawlers-group}")
    public void onReceiveNewUrl(URI url) {
        LOGGER.info("Received URL {}", url);
        Page page = extractor.extractLinks(url);
        pageProducer.producePage(page);
        urlsProducer.produceUrls(page.getUris());
    }
}
