package my.wiki.unifier;

import my.wiki.common.KafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@Import(KafkaConfiguration.class)
@EnableFeignClients(clients = GraphClient.class)
public class UnifierApplication {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(10)).setReadTimeout(Duration.ofSeconds(20)).build();
    }

    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            SpringApplication.run(UnifierApplication.class, args);
            watch.stop();
            System.out.println("Unifier context started in " + (watch.getTotalTimeSeconds()) + " seconds");
        } catch (Exception e) {
            System.out.println("Unable to start Unifier");
            e.printStackTrace();
        }
    }
}
