package ua.rd.foodorder.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.repository.LocationRepository;

/**
 * Created by Iaroslav Grytsaienko on 07.06.2016.
 */
public interface GenericService<E, PK> {

    Iterable<E> findAll();

    E findById(PK id);

    @Transactional
    E update(E entity);

    @Transactional
    void remove(PK id);

    @Transactional
    E save(E entity);
}
