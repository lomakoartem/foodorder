package ua.rd.foodorder.web.dto.service;

import java.util.List;

import ua.rd.foodorder.web.dto.domain.VendorDto;

public interface VendorDtoService {
	
	List<VendorDto> findAll();

    VendorDto findById(Long id);

    VendorDto update(VendorDto vendorDto);

    void remove(Long id);

    VendorDto save(VendorDto vendorDto);
}
