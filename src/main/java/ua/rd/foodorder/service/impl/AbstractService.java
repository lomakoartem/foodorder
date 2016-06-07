package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.service.GenericService;

import java.io.Serializable;

/**
 * Created by Iaroslav Grytsaienko on 07.06.2016.
 */
@Service
public abstract class AbstractService<E, PK extends Serializable> implements GenericService<E, PK> {

    @Autowired
    protected CrudRepository<E, PK> repository;

    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E findById(PK id) {
        return repository.findOne(id);
    }

    @Transactional
    @Override
    public E update(E entity) {
        return repository.save(entity);
    }

    @Transactional
    @Override
    public void remove(PK id) {
        repository.delete(id);
    }

    @Transactional
    @Override
    public E save(E entity) {
        return repository.save(entity);
    }
}

