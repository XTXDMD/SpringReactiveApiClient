package com.example.webfluxservice.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/11 15:25
 */
public class GreetingWebClient {
    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.get()
            .uri("/hello1")
            .accept(MediaType.TEXT_PLAIN)
            .exchange();

    public String getResult(){
        return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
    }
}
