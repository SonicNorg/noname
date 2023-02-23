package my.wiki.common;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

public class Page {
    private URL url;
    private OffsetDateTime lastChangeDate;
    private OffsetDateTime visitedDate;
    private List<URL> urls;
}
