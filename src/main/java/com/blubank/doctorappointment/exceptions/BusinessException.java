package com.blubank.doctorappointment.exceptions;

import lombok.Getter;

@Getter
public class BusinessException
        extends Exception
        implements IBusinessException {
    private static final long serialVersionUID = -9075958413755077206L;

    private final IErrorCode errorCode;
    private final String additionalInfo;
    private final Throwable cause;
    private final boolean enableSuppression;
    private final boolean writableStackTrace;

    public BusinessException(IErrorCode errorCode) {
        this(errorCode, "", false, true);
    }

    public BusinessException(IErrorCode errorCode, Throwable cause) {
        this(errorCode, "", cause, false, true);
    }

    public BusinessException(IErrorCode errorCode, String additionalInfo, Throwable cause) {
        this(errorCode, additionalInfo, cause, false, true);
    }

    public BusinessException(IErrorCode errorCode, String additionalInfo) {
        this(errorCode, additionalInfo, null, false, true);
    }

    protected BusinessException(IErrorCode errorCode, String additionalInfo, boolean enableSuppression, boolean writableStackTrace) {
        this(errorCode, additionalInfo, null, enableSuppression, writableStackTrace);
    }

    protected BusinessException(IErrorCode errorCode, String additionalInfo, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode.getMessage(), cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
        this.cause = cause;
        this.enableSuppression = enableSuppression;
        this.writableStackTrace = writableStackTrace;
    }
}
