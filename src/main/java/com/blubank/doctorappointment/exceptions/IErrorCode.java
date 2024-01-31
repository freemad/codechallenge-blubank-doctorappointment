package com.blubank.doctorappointment.exceptions;

public interface IErrorCode {
    int getCode();
    String getMessage();
    String getDetail();
}
