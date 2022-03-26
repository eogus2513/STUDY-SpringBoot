package com.study.test.global.redis;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private final String host;
    private final int port;

    public RedisProperties(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
