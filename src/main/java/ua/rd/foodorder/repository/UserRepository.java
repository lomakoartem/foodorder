package ua.rd.foodorder.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.rd.foodorder.domain.User;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
