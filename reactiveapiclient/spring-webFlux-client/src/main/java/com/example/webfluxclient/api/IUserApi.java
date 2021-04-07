package com.example.webfluxclient.api;

import com.example.webfluxclient.model.User;
import frame.annotation.ApiServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/24 15:24
 */
@ApiServer("http://localhost:8080/flux")
public interface IUserApi {

    @GetMapping("/getAllUser")
    Flux<User> getAllUser();

    @GetMapping("/getUserById/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    @PostMapping("/addUser")
    Flux<User> addUser(@RequestBody Mono<User> user);

    @DeleteMapping("/deleteUser/{id}")
    Mono<Void> deleteUser(@PathVariable("id") String id);


}
