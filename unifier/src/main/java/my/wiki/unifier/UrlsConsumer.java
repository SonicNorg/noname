package my.wiki.unifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsConsumer.class);
    private final UrlsProducer urlsProducer;
    private final UnifierService unifierService;

    public UrlsConsumer(UrlsProducer urlsProducer, UnifierService unifierService) {
        this.urlsProducer = urlsProducer;
        this.unifierService = unifierService;
    }

    @KafkaListener(topics = "${wiki-config.raw-urls-topic}", groupId = "${wiki-config.unifiers-group}")
    public void onReceiveNewUrl(URI uri) {
        LOGGER.info("Received URL {}", uri);
        if (unifierService.isUnique(uri)) {
            LOGGER.debug("URL {} is unique, publishing", uri);
            urlsProducer.produceUrl(uri);
        } else {
            LOGGER.debug("URL {} is NOT unique", uri);
        }
    }
}
