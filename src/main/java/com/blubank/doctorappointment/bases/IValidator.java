package com.blubank.doctorappointment.bases;

import com.blubank.doctorappointment.exceptions.BusinessException;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

public interface IValidator {
    public void setNext(IValidator next);
    public void setAdditionalInfo(String additionalInfo);
    public boolean validate() throws BusinessException;
}
