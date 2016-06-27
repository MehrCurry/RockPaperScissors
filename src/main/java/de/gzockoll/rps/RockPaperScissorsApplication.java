package de.gzockoll.rps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Just normal Spring Boot Application
 */
@SpringBootApplication
@ComponentScan(basePackages = "de.gzockoll.rps")
@SuppressWarnings("squid:S2095")
public class RockPaperScissorsApplication {

    /**
     * Main method to start the spring boot application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RockPaperScissorsApplication.class, args);
    }
}
