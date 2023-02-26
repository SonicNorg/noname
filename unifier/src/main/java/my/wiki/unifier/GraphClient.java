package my.wiki.unifier;

import my.wiki.api.GraphApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Graph", url = "http://localhost:10003")
public interface GraphClient extends GraphApi {
}
