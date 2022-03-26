package com.study.test.domain.exception;

import com.study.test.global.error.CustomException;
import com.study.test.global.error.ErrorCode;

public class UserExistsException extends CustomException {

    public static final CustomException EXCEPTION =
            new UserExistsException();

    private UserExistsException() {
        super(ErrorCode.USER_EXISTS);
    }

}
