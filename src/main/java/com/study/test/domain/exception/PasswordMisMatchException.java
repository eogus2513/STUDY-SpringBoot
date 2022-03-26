package com.study.test.domain.exception;

import com.study.test.global.error.CustomException;
import com.study.test.global.error.ErrorCode;

public class PasswordMisMatchException extends CustomException {

    public static final CustomException EXCEPTION =
            new PasswordMisMatchException();

    private PasswordMisMatchException() {
        super(ErrorCode.PASSWORD_MIS_MATCH);
    }

}
