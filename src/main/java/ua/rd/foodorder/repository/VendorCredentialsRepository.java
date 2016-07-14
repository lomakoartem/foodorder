package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;

import ua.rd.foodorder.domain.VendorCredentials;

public interface VendorCredentialsRepository extends CrudRepository<VendorCredentials, Long>{
}
