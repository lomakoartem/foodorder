package ua.rd.foodorder.service;

import ua.rd.foodorder.domain.Vendor;

/**
 * Created by Artem on 06.06.2016.
 */
public interface VendorService {

    Iterable<Vendor> findAll();

    Vendor findById(Long id);

    Vendor update(Vendor vendor);

    void remove(Long id);

    Vendor save(Vendor vendor);
}
