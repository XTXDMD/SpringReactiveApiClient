package com.example.webfluxservice.controller.fluxType;

import com.example.webfluxservice.model.User;
import com.example.webfluxservice.repository.UserRepository;
import com.example.webfluxservice.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/24 10:00
 */
@Component
@Slf4j
public class UserHandler {

    private UserRepository repository;

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * 查询所有用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll(), User.class);
    }

    /**
     * 查询用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(repository.findById(id), User.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }



    /**
     * 新增用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> addUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        log.info("调通createUser方法");
        return user.map(u ->{ CheckUtil.check(u.getName());
            return ServerResponse.ok().build();
        }
        ).then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(repository.saveAll(user), User.class));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(repository.saveAll(user),User.class);
    }

    /**
     * 更新用户
     */

//    public Mono<ServerResponse> updateUser(ServerRequest request){
//        String id = request.pathVariable("id");
//        Mono<User> user = request.bodyToMono(User.class);
//        Mono<User> userToUpdate = repository.findById(id);
//        userToUpdate.flatMap(u -> {
//
//        })

//        ServerResponse response;
//        user.map(u ->
//            repository.findById(id).flatMap(userToUpdate -> {
//                userToUpdate.setName(u.getName());
//                userToUpdate.setAge(u.getAge());
//                return repository.save(userToUpdate);
//            })
//        );
//    }

    /**
     * 删除用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id).flatMap(user -> repository.delete(user))
                .then(ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
