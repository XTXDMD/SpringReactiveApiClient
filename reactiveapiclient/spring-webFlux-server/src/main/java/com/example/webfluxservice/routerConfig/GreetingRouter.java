package com.example.webfluxservice.routerConfig;

import com.example.webfluxservice.controller.fluxType.UserHandler;
import com.example.webfluxservice.handler.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author zhy
 * @since 2021/3/11 15:15
 */
@Configuration
public class GreetingRouter {
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler, UserHandler userHandler){
        return RouterFunctions.nest(RequestPredicates.path("/flux"),
                RouterFunctions.route(RequestPredicates.GET("/hello1"),greetingHandler::hello)
                .andRoute(RequestPredicates.GET("/getAllUser"),userHandler::getAllUser)
                .andRoute(RequestPredicates.DELETE("/deleteUser/{id}"),userHandler::deleteUserById)
                .andRoute(RequestPredicates.POST("/addUser").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),userHandler::addUser)
                .andRoute(RequestPredicates.GET("/getUserById/{id}"),userHandler::getUserById)
//                .andRoute(RequestPredicates.PUT("/updateUser/{id}"),userHandler::updateUser)
        );
    }
}
