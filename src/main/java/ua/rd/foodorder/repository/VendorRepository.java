package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.foodorder.domain.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
