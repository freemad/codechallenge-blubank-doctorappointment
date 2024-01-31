package com.blubank.doctorappointment.bases;

import java.text.ParseException;

public interface IBaseModelConverter<T extends BaseEntity, D extends BaseDto<T>> {
    public T toEntity(D dto);
    public D toDto(T entity);
}
