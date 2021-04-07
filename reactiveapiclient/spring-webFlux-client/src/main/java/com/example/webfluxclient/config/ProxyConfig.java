package com.example.webfluxclient.config;

import frame.interfaces.ProxyCreator;
import frame.proxys.JDKProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhy
 * @since 2021/3/29 14:55
 */
@Configuration
public class ProxyConfig {
    @Bean
    ProxyCreator proxyCreator(){
        return new JDKProxyCreator();
    }
}
