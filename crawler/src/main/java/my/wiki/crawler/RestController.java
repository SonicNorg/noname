package my.wiki.crawler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final UrlsConsumer consumer;

    public RestController(UrlsConsumer consumer) {
        this.consumer = consumer;
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processUrl(@RequestParam URI uri) {
        if (uri.toString().startsWith("https://ru.wikipedia.org/wiki")) {
            consumer.onReceiveNewUrl(uri);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
