package my.wiki.graph.repository;

import org.springframework.data.repository.CrudRepository;

public interface GraphRepository extends CrudRepository<GraphNode, Integer> {
}