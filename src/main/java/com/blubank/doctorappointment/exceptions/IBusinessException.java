package com.blubank.doctorappointment.exceptions;

public interface IBusinessException {
    IErrorCode getErrorCode();

    String getAdditionalInfo();

    Throwable getCause();

    boolean isEnableSuppression();

    boolean isWritableStackTrace();
}

