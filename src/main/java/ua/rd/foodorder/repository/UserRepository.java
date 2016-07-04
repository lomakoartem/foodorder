package ua.rd.foodorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.rd.foodorder.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    
    Page<User> findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(String firstName, String lastName, Pageable pageRequest);
    
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageRequest);
    
    Iterable<User> findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(String firstName, String lastName);
    
    Iterable<User> findByNameContainingIgnoreCase(String name);
}
