package com.study.test.domain.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash
public class RefreshToken {

    @Id
    private String accountId;

    @Indexed
    private String token;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(String accountId, String token, Long ttl) {
        this.accountId = accountId;
        this.token = token;
        this.ttl = ttl;
    }

    public void updateToken(String token, Long ttl) {
        this.token = token;
        this.ttl = ttl;
    }

}
