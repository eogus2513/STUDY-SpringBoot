package com.study.test.domain.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String accountId;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(length = 60, nullable = false)
    private String password;

    @Builder
    public User(String accountId, String name, String password) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
    }

}
