package com.blubank.doctorappointment.bases;

import com.blubank.doctorappointment.exceptions.BusinessException;
import com.blubank.doctorappointment.exceptions.IErrorCode;

import java.util.HashMap;
import java.util.function.Predicate;

public class BaseValidator implements IValidator {

    private final IErrorCode errorCode;
    private String additionalInfo;
    private final Predicate<HashMap<String, Object>> validation;
    private final HashMap<String, Object> inputs;
    private IValidator next;

    protected BaseValidator(HashMap<String, Object> inputs, Predicate<HashMap<String, Object>> validation, IErrorCode errorCode) {
        this.validation = validation;
        this.inputs = inputs;
        this.errorCode = errorCode;
    }

    @Override
    public void setNext(IValidator next) {
        this.next = next;
    }

    @Override
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean validate() throws BusinessException {
        if (!validation.test(inputs)) {
            throw new BusinessException(errorCode, additionalInfo);
        } else {
            if (next != null) {
                next.validate();
            }
        }
        return true;
    }
}
