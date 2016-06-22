package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.foodorder.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {

}
