package fr.uga.miage.m1.reactor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@ComponentScan(basePackages = "fr.uga.miage.m1")
public class PCReactorApplication {
    public static void main(String[] args) {
        SpringApplication.run(PCReactorApplication.class, args);
    }

}
