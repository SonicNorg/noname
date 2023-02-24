package my.wiki.graph.repository;

import my.wiki.common.Page;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;

import static my.wiki.common.Util.hash;

public final class ModelMapper {
    private ModelMapper() {
    }

    public static Page map(GraphNode node) throws URISyntaxException {
        return new Page(
                new URI(node.getUrl()),
                node.getLastChange(),
                node.getVisitedAt(),
                node.getConnectedTo().stream().map(n -> {
                    try {
                        return new URI(n.getUrl());
                    } catch (URISyntaxException e) {
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList())
        );
    }

    public static GraphNode map(Page page) {
        GraphNode node = new GraphNode();
        node.setCode(hash(page.getUri().toString()));
        node.setUrl(page.getUri().toString());
        node.setLastChange(page.getLastChangeDate());
        node.setVisitedAt(page.getVisitedDate());
        node.setConnectedTo(page.getUris().stream().map(u -> {
            GraphNode n = new GraphNode();
            n.setCode(hash(u.toString()));
            n.setUrl(u.toString());
            return n;
        }).collect(Collectors.toSet()));
        return node;
    }

    public static GraphNode map(URI uri) {
        GraphNode node = new GraphNode();
        node.setCode(hash(uri.toString()));
        node.setUrl(uri.toString());
        return node;
    }
}
