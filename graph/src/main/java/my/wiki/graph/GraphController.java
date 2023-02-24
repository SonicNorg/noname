package my.wiki.graph;

import my.wiki.api.ServiceApi;
import my.wiki.api.UiApi;
import my.wiki.graph.repository.GraphNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GraphController implements ServiceApi, UiApi {
    private final GraphService service;

    public GraphController(GraphService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Boolean> isUnique(URI uri) {
        return ResponseEntity.ok(service.isUnique(uri));
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
