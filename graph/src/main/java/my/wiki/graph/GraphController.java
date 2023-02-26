package my.wiki.graph;

import my.wiki.api.GraphApi;
import my.wiki.common.Page;
import my.wiki.graph.repository.GraphNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static my.wiki.common.Util.hash;

@RestController
public class GraphController implements GraphApi {
    private final GraphService service;

    public GraphController(GraphService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Page> findByUri(URI uri) {
        try {
            return ResponseEntity.ok(service.getByCode(hash(uri.toString())));
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<URI>> findPath(URI src, URI dst) {
        try {
            List<GraphNode> path = service.findPath(src, dst);
            return ResponseEntity.ok(path.stream().map(node -> {
                try {
                    return new URI(node.getUrl());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (GraphService.NodeNotFoundException e) {
            return ResponseEntity.status(400).body(Collections.singletonList(e.getUri()));
        }
    }

    @Override
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }
}
