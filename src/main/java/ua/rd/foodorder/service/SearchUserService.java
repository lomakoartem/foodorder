package ua.rd.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ua.rd.foodorder.domain.User;

public interface SearchUserService {
    
    public Page<User> searchUserByName(String searchTerm, PageRequest pageRequest);
}
