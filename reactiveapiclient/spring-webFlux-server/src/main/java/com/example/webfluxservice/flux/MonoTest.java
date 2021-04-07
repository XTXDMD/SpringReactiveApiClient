package com.example.webfluxservice.flux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/22 9:36
 */

@RestController
@Slf4j
public class MonoTest {
    @RequestMapping("/1")
    public String get1() throws InterruptedException {
        log.info("start");
        String result = CreateStr();
        log.info("end");
        return result;
    }

    public String CreateStr() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello mono";
    }

    @RequestMapping("/2")
    public Mono<String> get2(){
        log.info("start");
        Mono<String> result = Mono.fromSupplier(this::CreateStr);
        log.info("end");
        return result;
    }



}
