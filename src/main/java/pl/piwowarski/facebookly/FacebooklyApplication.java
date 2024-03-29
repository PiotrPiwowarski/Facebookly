package pl.piwowarski.facebookly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class FacebooklyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacebooklyApplication.class, args);
    }
}
