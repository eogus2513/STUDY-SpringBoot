package com.study.test.global.error.ErrorResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BindErrorResponse {

    private final int status;
    private final List<String> messages;

}
