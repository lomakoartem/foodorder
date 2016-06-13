package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;



@Service
@Transactional
public class SimpleVendorService implements VendorService {

	@Autowired
	private VendorRepository vendorRepository;
	
    @Override
    public Vendor update(Vendor vendor) {

        Vendor dbVendor = vendorRepository.findOne(vendor.getId());

        if (dbVendor == null) {
            throw new EntityNotFoundException(vendor.getId());
        }
        
        dbVendor.setName(vendor.getName());
        dbVendor.setPhone(vendor.getPhone());
        dbVendor.setEmail(vendor.getEmail());
        dbVendor.setActive(vendor.isActive());
        dbVendor.setLocations(vendor.getLocations());

        return vendorRepository.save(dbVendor);
    }

	@Override
	public Iterable<Vendor> findAll() {
		return vendorRepository.findAll();
	}

	@Override
	public Vendor findById(Long id) {
		Vendor vendor = vendorRepository.findOne(id);

		if (vendor == null) {
			throw new EntityNotFoundException(id);
		}

		return vendor;
	}

	@Override
	public void remove(Long id) {
		Vendor dbVendor = vendorRepository.findOne(id);

		dbVendor.setActive(false);

		vendorRepository.save(dbVendor);
		
	}

	@Override
	public Vendor save(Vendor vendor) {
		return vendorRepository.save(vendor);
	}

}
