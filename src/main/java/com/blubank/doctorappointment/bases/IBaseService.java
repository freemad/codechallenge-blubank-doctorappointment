package com.blubank.doctorappointment.bases;

import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBaseService<T extends BaseEntity> {

    public Optional<T> findById(UUID id);
    public T save(T entity);
    public T delete(UUID id) throws NotFoundException;
    public List<T> findAll();
}
