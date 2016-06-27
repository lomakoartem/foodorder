package ua.rd.foodorder.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.domain.User;

public interface UserService {

    Iterable<User> findAll();

    User findById(Long id);

    User update(User user);

    void remove(Long id);

    User save(User user);
    
    Page<User> getPageOfUsers(PageRequest pageRequest);
    
    List<User> parseExcelDocument(MultipartFile file);
    
    Iterable<User> save(List<User> users);
}
