package my.wiki.graph;

import my.wiki.common.KafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@Import(KafkaConfiguration.class)
public class GraphApplication {

    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            SpringApplication.run(GraphApplication.class, args);
            watch.stop();
            System.out.println("Graph context started in " + (watch.getTotalTimeSeconds()) + " seconds");
        } catch (Exception e) {
            System.out.println("Unable to start Graph");
            e.printStackTrace();
        }
    }
}
