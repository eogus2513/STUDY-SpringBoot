package com.study.test.domain.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private final String accountId;
    private final String name;

}
