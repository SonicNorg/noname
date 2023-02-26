package my.wiki.graph;

import my.wiki.common.Page;
import my.wiki.graph.repository.GraphNode;
import my.wiki.graph.repository.GraphRepository;
import my.wiki.graph.repository.ModelMapper;
import my.wiki.graph.repository.PathRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static my.wiki.common.Util.hash;

@Service
public class GraphService {
    private final GraphRepository repository;
    private final PathRepository pathRepo;

    public GraphService(GraphRepository repository, PathRepository pathRepo) {
        this.repository = repository;
        this.pathRepo = pathRepo;
    }

    public Page getByCode(int code) throws URISyntaxException {
        return ModelMapper.map(repository.findById(code).orElse(null));
    }

    public List<GraphNode> findPath(URI src, URI dst) throws NodeNotFoundException, MalformedURLException {
        int srcHash = hash(src.toURL().toString());
        if (!repository.existsById(srcHash)) {
            throw new NodeNotFoundException(src);
        }
        int dstHash = hash(dst.toURL().toString());
        if (!repository.existsById(dstHash)) {
            throw new NodeNotFoundException(dst);
        }
        return pathRepo.findPath(srcHash, dstHash);
    }

    public int getCount() {
        return (int) repository.count();
    }

    public static class NodeNotFoundException extends Exception {
        private final URI uri;

        public NodeNotFoundException(URI uri) {
            super("Node " + uri + " not found in graph!");
            this.uri = uri;
        }

        public URI getUri() {
            return uri;
        }
    }
}
