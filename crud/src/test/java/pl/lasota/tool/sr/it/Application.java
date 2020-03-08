package pl.lasota.tool.sr.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"pl.lasota.tool.sr.repository", "pl.lasota.tool.sr.it"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}