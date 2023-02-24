package my.wiki.common;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public class Page {
    private URI uri;
    private LocalDate lastChangeDate;
    private LocalDate visitedDate;
    private List<URI> uris;

    //Для десериализации нужен конструктор по умолчанию
    public Page() {
    }

    public Page(URI uri, LocalDate lastChangeDate, LocalDate visitedDate, List<URI> uris) {
        this.uri = uri;
        this.lastChangeDate = lastChangeDate;
        this.visitedDate = visitedDate;
        this.uris = uris;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI url) {
        this.uri = url;
    }

    public List<URI> getUris() {
        return uris;
    }

    public void setUris(List<URI> urls) {
        this.uris = urls;
    }

    public LocalDate getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(LocalDate lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public LocalDate getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(LocalDate visitedDate) {
        this.visitedDate = visitedDate;
    }
}
