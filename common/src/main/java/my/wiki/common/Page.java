package my.wiki.common;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class Page {
    private URL url;
    private LocalDate lastChangeDate;
    private LocalDate visitedDate;
    private List<URL> urls;

    public Page(URL url, LocalDate lastChangeDate, LocalDate visitedDate, List<URL> urls) {
        this.url = url;
        this.lastChangeDate = lastChangeDate;
        this.visitedDate = visitedDate;
        this.urls = urls;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
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
