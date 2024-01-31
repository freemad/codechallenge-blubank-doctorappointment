package com.blubank.doctorappointment.exceptions;

import com.blubank.doctorappointment.global.Constant;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ErrorCode implements IErrorCode {

    LOCKED_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 1,
            "LOCKED_EXCEPTION", "is locked"),
    NOT_FOUND_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 2,
            "NOT_FOUND_EXCEPTION", "not found"),
    BAD_REQUEST_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 3,
            "BAD_REQUEST_EXCEPTION", "bad request"),
    BAD_PARAM_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 4,
            "BAD_PARAM_EXCEPTION", "bad param"),
    GENERAL_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 5,
            "GENERAL_EXCEPTION", "general exception"),
    INTERNAL_SERVER_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 6,
            "INTERNAL_SERVER_EXCEPTION", "internal server exception"),
    TRANSACTION_EXCEPTION(Constant.COMMON_ERROR_CODE_START_INDEX + 7,
            "TRANSACTION_EXCEPTION", "transaction failed");
    private final int code;
    private final String message;
    private final String detail;

    ErrorCode(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static List<ErrorCode> asList() {
        return Stream.of(ErrorCode.values()).collect(Collectors.toList());
    }
}
