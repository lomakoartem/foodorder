package ua.rd.foodorder.service;

import ua.rd.foodorder.domain.Vendor;

public interface VendorService {
	
	Iterable<Vendor> findAll();

    Vendor findById(Long id);

    Vendor update(Vendor vendor);

    void remove(Long id);

    Vendor save(Vendor vendor);

    boolean generatePasswordAndSendByMail(Long id);
    
    char[] generatePasswordAndSaveInDatabase(Long id);
    
    boolean sendPasswordByMail(Long id, char[] password);
    

}
