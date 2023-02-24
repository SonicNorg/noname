package my.wiki.crawler;

import my.wiki.common.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LinksExtractor {
    private static final String PREFIX = "https://ru.wikipedia.org";
    private final Pattern regex = Pattern.compile("<a href=\"(/wiki/[^#\"]+)");

    private final RestTemplate restTemplate;
    private final DateExtractor dateExtractor;

    public LinksExtractor(RestTemplate restTemplate, DateExtractor dateExtractor) {
        this.restTemplate = restTemplate;
        this.dateExtractor = dateExtractor;
    }

    public Page extractLinks(URI url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String content = response.getBody();
            List<URI> urls = getRelativeUrlsFromPage(content).stream().map(str -> {
                try {
                    return new URI(java.net.URLDecoder.decode(new URI(PREFIX.concat(str)).toString(), StandardCharsets.UTF_8));
                } catch (URISyntaxException e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            return new Page(url, dateExtractor.extract(content), LocalDate.now(), urls);
        } else {
            return null;
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
