package ua.rd.foodorder.service;

import org.springframework.data.domain.Page;

import ua.rd.foodorder.domain.User;

public interface UserService {

    Iterable<User> findAll();

    User findById(Long id);

    User update(User user);

    void remove(Long id);

    User save(User user);
    
    Page<User> getPageOfUsers(Integer pageNumber, Integer size);
}
