package com.study.test.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    EXPIRED_JWT(401, "Expired Jwt"),
    INVALID_JWT(401, "Invalid Jwt"),
    PASSWORD_MIS_MATCH(401, "Password Mis Match"),

    USER_NOT_FOUND(404, "User Not Found"),
    REFRESH_TOKEN_NOT_FOUND(404, "RefreshToken Not Found"),

    USER_EXISTS(409, "User Exists"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;

}
