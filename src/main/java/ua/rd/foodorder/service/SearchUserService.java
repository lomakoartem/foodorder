package ua.rd.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ua.rd.foodorder.domain.User;

public interface SearchUserService {
    
    public Page<User> searchPageOfUsers(String searchTerm, PageRequest pageRequest);
    
    public Iterable<User> searchUserByTerm(String searchTerm, Sort sort);
}
