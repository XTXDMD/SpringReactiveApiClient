package com.example.webfluxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        GreetingWebClient gwc = new GreetingWebClient();
//        System.out.println(gwc.getResult());
    }
}
