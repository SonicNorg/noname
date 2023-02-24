package my.wiki.graph.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PathRepository {
    private final JdbcTemplate template;

    private static final String SELECT_QUERY = "SELECT * FROM nodes RIGHT JOIN unnest(" +
            "(WITH RECURSIVE paths (src, dst, path) AS (" +
            "                    SELECT e.src, e.dst, ARRAY[e.src, e.dst]" +
            "                    FROM edges e" +
            "                UNION" +
            "                    SELECT p.src, e.dst, p.path || ARRAY[e.dst]" +
            "                    FROM paths p" +
            "                    JOIN edges e" +
            "                    ON p.dst = e.src AND e.dst != ALL(p.path)" +
            "            )" +
            "            SELECT path FROM paths WHERE src = ? AND dst = ? ORDER BY array_length(path, 1) LIMIT 1" +
            ")::int[]" +
            ") WITH ORDINALITY as a on code=a ORDER BY ordinality;";

    public PathRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<GraphNode> findPath(int src, int dst) {
        return template.query(SELECT_QUERY, new GraphNodeRowMapper(), src, dst);
    }
}
