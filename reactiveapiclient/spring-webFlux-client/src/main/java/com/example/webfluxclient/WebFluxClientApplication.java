package com.example.webfluxclient;

import com.example.webfluxclient.api.IUserApi;
import frame.interfaces.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebFluxClientApplication {

    //注入userApi
    @Bean
    FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserApi>() {

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }

            /**
             * 返回代理类
             * @return
             * @throws Exception
             */
            @Override
            public IUserApi getObject() {
                return (IUserApi) proxyCreator.createProxy(this.getObjectType());
            }
        };

    }

    public static void main(String[] args) {

        SpringApplication.run(WebFluxClientApplication.class, args);

    }


}
