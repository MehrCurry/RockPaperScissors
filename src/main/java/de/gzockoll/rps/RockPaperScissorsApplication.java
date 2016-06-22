package de.gzockoll.rps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "de.gzockoll.rps")
@EnableWebMvc
public class RockPaperScissorsApplication {

	public static void main(String[] args) {
        SpringApplication.run(RockPaperScissorsApplication.class, args);
    }
}
