package com.example.webfluxservice.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/11 15:02
 */
@Component
public class GreetingHandler {
    public Mono<ServerResponse> hello (ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("hello springWebFlux"));
    }
}
