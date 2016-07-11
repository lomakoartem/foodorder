package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGeneratorAndHashing;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;

@Service
@Transactional
public class SimpleVendorService implements VendorService {

	private VendorRepository vendorRepository;

	private PasswordGeneratorAndHashing passwordGenerator;



	@Override
    public Vendor update(Vendor vendor) {

        Vendor vendorInBD = vendorRepository.findOne(vendor.getId());

        if (vendorInBD == null) {
            throw new EntityNotFoundException(vendor.getId());
        }
        
        vendorInBD.setName(vendor.getName());
        vendorInBD.setAdditionalInfo(vendor.getAdditionalInfo());
        vendorInBD.setEmail(vendor.getEmail());
        vendorInBD.setActive(vendor.isActive());
        vendorInBD.setLocations(vendor.getLocations());
		vendorInBD.setPassword(vendor.getPassword());
		return vendorRepository.save(vendorInBD);
    }

	@Override
	public Iterable<Vendor> findAll() {
		return vendorRepository.findAll();
	}

	@Override
	public Vendor findById(Long id) {
		Vendor vendorInBD = vendorRepository.findOne(id);

		if (vendorInBD == null) {
			throw new EntityNotFoundException(id);
		}

		return vendorInBD;
	}

	@Override
	public void remove(Long id) {
		Vendor vendorInDB = vendorRepository.findOne(id);

		if(vendorInDB == null){
			throw new EntityNotFoundException(id);
		}

		vendorInDB.setActive(false);

		vendorRepository.save(vendorInDB);
		
	}

	@Override
	public Vendor save(Vendor vendor) {
		return vendorRepository.save(vendor);
	}


	@Autowired
	public void setVendorRepository(VendorRepository vendorRepository , PasswordGeneratorAndHashing passwordGenerator) {
		this.vendorRepository = vendorRepository;
		this.passwordGenerator = passwordGenerator;
	}
	
	
}
