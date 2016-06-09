package ua.rd.foodorder.web.dto.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.service.VendorService;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.service.MappingVendorDtoService;
import ua.rd.foodorder.web.dto.service.VendorDtoService;


@Service
public class VendorDtoServiceImpl implements VendorDtoService{

	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private MappingVendorDtoService mappingService;
	
	@Override
	@Transactional(readOnly = true)
	public List<VendorDto> findAll() {
		Iterable<Vendor> vendors = vendorService.findAll();
		List<VendorDto> vendorDtos = new ArrayList<>();
		for (Vendor vendor : vendors){
			vendorDtos.add(mappingService.convertToDto(vendor));
		}
		return vendorDtos;
	}

	@Override
	@Transactional(readOnly = true)
	public VendorDto findById(Long id) {
		Vendor vendor = vendorService.findById(id);
		return mappingService.convertToDto(vendor);
	}

	@Override
	@Transactional
	public VendorDto update(VendorDto vendorDto) {
		Vendor vendor = mappingService.convertToEntity(vendorDto);
		vendor = vendorService.update(vendor);
		return mappingService.convertToDto(vendor);
	}

	@Override
	@Transactional
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public VendorDto save(VendorDto vendorDto) {
		Vendor vendor = mappingService.convertToEntity(vendorDto);
		vendor = vendorService.save(vendor);
		return mappingService.convertToDto(vendor);
	}

	
}
