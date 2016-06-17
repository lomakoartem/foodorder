package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.UserService;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */

@Component
@Transactional
public class SimpleUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findByPageQuantity(int pageNumber){
        return  userRepository.findAll(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User update(User user) {
        User userInDB = userRepository.findOne(user.getId());

        if(userInDB == null){
            throw new EntityNotFoundException(user.getId());
        }

        user.setActive(userInDB.isActive());
        user.setAdmin(userInDB.isAdmin());
        user.setEmail(userInDB.getName());
        user.setName(userInDB.getName());

        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        User userInDB = userRepository.findOne(id);

        if(userInDB == null){
            throw new EntityNotFoundException(id);
        }

        userInDB.setActive(false);

        userRepository.save(userInDB);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
