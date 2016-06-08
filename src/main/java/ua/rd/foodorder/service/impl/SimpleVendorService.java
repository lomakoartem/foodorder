package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;


/**
 * Created by Artem on 06.06.2016.
 */
@Service
@Transactional
public class SimpleVendorService extends AbstractService<Vendor, Long> implements VendorService {

    @Override
    public Vendor update(Vendor vendor) {

        Vendor dbVendor = repository.findOne(vendor.getId());

        dbVendor.setName(vendor.getName());
        dbVendor.setPhone(vendor.getPhone());
        dbVendor.setEmail(vendor.getEmail());
        dbVendor.setActive(vendor.isActive());

        return repository.save(dbVendor);
    }

}
