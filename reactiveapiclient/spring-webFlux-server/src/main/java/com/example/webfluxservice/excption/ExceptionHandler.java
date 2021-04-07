package com.example.webfluxservice.excption;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/24 14:33
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        //设置响应头400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        //异常信息
        String errorMsg = toStr(ex);

        DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());

        return response.writeWith(Mono.just(db));
    }

    private String toStr(Throwable ex) {
        if (ex instanceof CheckException){
            CheckException checkException = (CheckException) ex;
            return checkException.getFieldName() + ": invalid field :" + checkException.getFieldValue();
        }else{
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
}
