package com.example.webfluxclient.controller;

import com.example.webfluxclient.api.IUserApi;
import com.example.webfluxclient.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/29 9:27
 */

@RestController
@Slf4j
public class TestController {

    @Autowired
    IUserApi iUserApi;

    @GetMapping("/")
    public void getAll(){
        //测试
//        iUserApi.getAllUser();
//        iUserApi.getUserById("11111111");
//        iUserApi.deleteUser("2222222");
//        iUserApi.addUser(Mono.just(User.builder().name("xiaogang").age(10).build()));

//
//        Flux<User> allUser = iUserApi.getAllUser();
//        allUser.subscribe(System.out::println);

//        String id = "60595e7cfe31766e2f6a80901";
//        iUserApi.getUserById(id).subscribe(user -> {System.out.println("找到用户:" + user);},
//                e -> {
//                    System.err.println("找不到用户" + e.getMessage());
//                });

//        Mono<User> userById = iUserApi.getUserById(id);
//        userById.subscribe(System.out::println);

        Flux<User> user = iUserApi.addUser(Mono.just(User.builder().name("admin").age(111).build()));
        user.subscribe(u -> {
            System.out.println("成功" + u);
        },e -> {
            System.err.println("失败" + e.getMessage());
        });
    }
}
