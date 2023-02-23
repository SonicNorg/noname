package my.wiki.crawler;

import my.wiki.common.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class UrlsProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsProducer.class);
    private final KafkaTemplate<String, URL> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    //TODO нельзя это оставлять: при запуске нескольких экземпляров это сработает у каждого. Это только для отладки.
    private final URL startUrl = new URL("https://ru.wikipedia.org");

    public UrlsProducer(KafkaTemplate<String, URL> kafkaTemplate, KafkaProperties kafkaProperties) throws MalformedURLException {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    //TODO нельзя это оставлять: при запуске нескольких экземпляров это сработает у каждого. Это только для отладки.
    @PostConstruct
    public void firstUrl() {
        LOGGER.info("Auto produce first url {}", startUrl);
        kafkaTemplate.send(kafkaProperties.getUniqueUrlsTopic(), startUrl);
    }

    public void produceUrls(List<URL> urls) {
        LOGGER.info("Publishing urls: {}", urls);
        urls.forEach(url -> kafkaTemplate.send(kafkaProperties.getRawUrlsTopic(), url));
    }
}
