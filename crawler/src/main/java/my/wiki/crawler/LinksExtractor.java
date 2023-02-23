package my.wiki.crawler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class LinksExtractor {
    private static final String PREFIX = "https://ru.wikipedia.org";
    private final Pattern regex = Pattern.compile("<a href=\"(/[^#\"]+)");

    private final RestTemplate restTemplate;

    public LinksExtractor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<URL> extractLinks(URL url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return getRelativeUrlsFromPage(response.getBody()).stream().map(str -> {
                try {
                    return new URL(PREFIX.concat(str));
                } catch (MalformedURLException e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            return emptyList();
        }
    }

    private List<String> getRelativeUrlsFromPage(String content) {
        List<String> result = new ArrayList<>();
        Matcher matcher = regex.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }
}
