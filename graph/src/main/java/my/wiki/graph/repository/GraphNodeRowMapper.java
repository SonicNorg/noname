package my.wiki.graph.repository;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GraphNodeRowMapper implements RowMapper<GraphNode> {
    @Override
    public GraphNode mapRow(ResultSet rs, int rowNum) throws SQLException {
        GraphNode graphNode = new GraphNode();
        graphNode.setCode(rs.getInt("code"));
        graphNode.setUrl(rs.getString("url"));
        graphNode.setUrl(rs.getString("url"));
        graphNode.setLastChange(rs.getObject("last_change", LocalDate.class));
        graphNode.setVisitedAt(rs.getObject("visited_at", LocalDate.class));
        return graphNode;
    }
}
