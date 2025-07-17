package br.com.casacultural;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CasaCultural extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.out.println("Iniciando aplicação...");
        SpringApplication.run(CasaCultural.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CasaCultural.class);
    }
}
