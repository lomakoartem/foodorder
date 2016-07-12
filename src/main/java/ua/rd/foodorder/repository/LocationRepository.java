package ua.rd.foodorder.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ua.rd.foodorder.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {

	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM locations_vendors WHERE location_id = :locationId")
	void removeVendorReferenceToLocation(@Param("locationId") Long id);
}
