package com.example.webfluxservice.repository;

import com.example.webfluxservice.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author zhy
 * @since 2021/3/23 10:10
 */

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Flux<User> findByAgeBetween(int star, int end);

    @Query("{'age':{'$gte':20,'$lte':30}}")
    Flux<User> oldUser();
}
