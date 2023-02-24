package my.wiki.graph.repository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "nodes")
public class GraphNode {
    @Id
    private int code;

    private String url;

    @Column(name = "last_change")
    private LocalDate lastChange;

    @Column(name = "visited_at")
    private LocalDate visitedAt;

    @ManyToMany
    @JoinTable(
            name = "edges",
            joinColumns = @JoinColumn(name = "src"),
            inverseJoinColumns = @JoinColumn(name = "dst"))
    private Set<GraphNode> connectedTo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    public LocalDate getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(LocalDate visitedAt) {
        this.visitedAt = visitedAt;
    }

    public Set<GraphNode> getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(Set<GraphNode> connectedTo) {
        this.connectedTo = connectedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return code == graphNode.code;
    }

    @Override
    public int hashCode() {
        return code;
    }
}
