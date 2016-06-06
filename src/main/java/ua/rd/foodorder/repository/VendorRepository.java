package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.foodorder.domain.Vendor;

/**
 * Created by Artem on 06.06.2016.
 */
public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
