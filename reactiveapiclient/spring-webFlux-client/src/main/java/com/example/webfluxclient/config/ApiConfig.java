package com.example.webfluxclient.config;

import com.example.webfluxclient.api.IUserApi;
import frame.interfaces.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhy
 * @since 2021/4/7 17:09
 */
@Configuration
public class ApiConfig {
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
}
