package ua.rd.foodorder.service;

import ua.rd.foodorder.domain.Vendor;

public interface VendorService {
	
	Iterable<Vendor> findAll();

    Vendor findById(Long id);

    Vendor update(Vendor vendor);

    void remove(Long id);

    Vendor save(Vendor vendor);

}
