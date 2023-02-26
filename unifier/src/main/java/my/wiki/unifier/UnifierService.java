package my.wiki.unifier;

import my.wiki.api.GraphApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;

@Service
public class UnifierService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnifierService.class);
    private final GraphApi api;

    public UnifierService(GraphApi api) {
        this.api = api;
    }

    public boolean isUnique(URI uri) {
        var found = api.findByUri(uri.toString());
        LOGGER.debug("Checking if URL {} is unique: response status {}, body {}", uri, found.getStatusCode(), found.getBody());
        return (found.getBody() == null || found.getBody().getVisitedDate() == null)
                || (found.getBody().getVisitedDate() != null
                && found.getBody().getVisitedDate().plusDays(1).isBefore(LocalDate.now()));
    }
}
