package com.blubank.doctorappointment.bases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService<T extends BaseEntity> implements IBaseService<T> {

    private final JpaRepository<T, UUID> repository;

    public BaseService(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T delete(UUID id) throws NotFoundException {
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            repository.delete(optionalEntity.get());
            return optionalEntity.get();
        } else {
            throw new NotFoundException("");
        }
    }

    @Override
    @Transactional
    public List<T> findAll() {
        return repository.findAll();
    }
}
