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
    public ResponseEntity<Page> findByUri(String uri) {
        try {
            return ResponseEntity.ok(service.getByCode(hash(new URI(uri).toString())));
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<String>> findPath(String src, String dst) {
        try {
            List<GraphNode> path = service.findPath(new URI(src), new URI(dst));
            return ResponseEntity.ok(path.stream().map(GraphNode::getUrl).collect(Collectors.toList()));
        } catch (MalformedURLException | URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        } catch (GraphService.NodeNotFoundException e) {
            return ResponseEntity.status(400).body(Collections.singletonList(e.getUri().toString()));
        }
    }

    @Override
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }
}
