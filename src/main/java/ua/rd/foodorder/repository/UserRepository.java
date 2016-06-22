package ua.rd.foodorder.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.rd.foodorder.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
