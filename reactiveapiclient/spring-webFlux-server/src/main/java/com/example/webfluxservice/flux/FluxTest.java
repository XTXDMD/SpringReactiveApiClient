package com.example.webfluxservice.flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zhy
 * @since 2021/3/22 10:02
 */
@RestController
public class FluxTest {
    @RequestMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux(){
        Flux<String> result = Flux.fromStream(IntStream.range(1,5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data--" + i;
        }));
        return result;
    }
}
