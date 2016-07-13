package ua.rd.foodorder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ua.rd.foodorder.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    
    Page<User> findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(String firstName, String lastName, Pageable pageRequest);
    
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageRequest);
    
    List<User> findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(String firstName, String lastName, Sort sort);
    
    List<User> findByNameContainingIgnoreCase(String name, Sort sort);
    
    @Query("SELECT count(u) FROM User u WHERE u.name <= :name ")
    Integer countNamesOfUsersThatLessNameOfNewUser(@Param("name")String name);
    
    Optional<User> findByName(String name);
    
    Optional<User> findByUpsaLink(String upsaLink);
}
