package my.wiki.unifier;

import my.wiki.api.GraphApi;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;

@Service
public class UnifierService {
    private final GraphApi api;

    public UnifierService(GraphApi api) {
        this.api = api;
    }

    public boolean isUnique(URI uri) {
        var found = api.findByUri(uri);
        return found.getBody() == null
                || (found.getBody().getVisitedDate() != null
                && found.getBody().getVisitedDate().plusDays(1).isBefore(LocalDate.now()));
    }
}
