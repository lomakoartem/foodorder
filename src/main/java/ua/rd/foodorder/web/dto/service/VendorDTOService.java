package ua.rd.foodorder.web.dto.service;

import java.util.List;

import ua.rd.foodorder.web.dto.domain.VendorDTO;

public interface VendorDTOService {
	
	List<VendorDTO> findAll();

    VendorDTO findById(Long id);

    VendorDTO update(VendorDTO vendorDTO);

    void remove(Long id);

    VendorDTO save(VendorDTO vendorDTO);
}
