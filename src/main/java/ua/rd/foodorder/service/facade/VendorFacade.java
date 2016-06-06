package ua.rd.foodorder.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.service.VendorService;

/**
 * Created by Artem on 06.06.2016.
 */
@Service
public class VendorFacade {

    @Autowired
    private VendorService vendorService;

    public Vendor findByIdAndCheck(Long id){
        Vendor vendor = vendorService.findById(id);

        if (vendor == null) {
            throw new EntityNotFoundException(id);
        }

        return vendor;
    }
    public void remove(Long id){
        vendorService.remove(id);
    }

    public Iterable<Vendor> getVendorList(){
        return vendorService.findAll();
    }

    public Vendor editVendor(Long id, Vendor vendor){
       Vendor dbVendor = vendorService.findById(id);

        if (dbVendor == null) {
            throw new EntityNotFoundException(id);
        }

        return vendorService.update(vendor);
    }

    public Vendor addVendor(Vendor vendor){
        return vendorService.save(vendor);
    }
}
