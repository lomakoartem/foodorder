package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.foodorder.domain.Location;

/**
 * Created by Iaroslav Grytsaienko on 30.05.2016.
 */

public interface LocationRepository extends CrudRepository<Location, Long> {

}
