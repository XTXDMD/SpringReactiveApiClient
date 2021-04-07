package com.example.webfluxservice.controller.annotationType;

import com.example.webfluxservice.model.User;
import com.example.webfluxservice.repository.UserRepository;
import com.example.webfluxservice.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * @author zhy
 * @since 2021/3/23 10:13
 */

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获取全部
     *
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * 流式获取
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAllStream() {
        return userRepository.findAll();
    }


    /**
     * 新增
     *
     * @param user
     * @return
     */
    @PostMapping("/insert")
    public Mono<User> createUser(@Valid @RequestBody User user) {
        user.setId(null);
        CheckUtil.check(user.getName());
        return userRepository.save(user);
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return userRepository.findById(id)
                .flatMap(userToupdate -> {
                    userToupdate.setName(user.getName());
                    userToupdate.setAge(user.getAge());
                    return userRepository.save(userToupdate);
                })
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<User>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
