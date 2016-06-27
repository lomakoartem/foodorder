package ua.rd.foodorder.web.dto.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.service.VendorService;
import ua.rd.foodorder.web.dto.domain.VendorDTO;
import ua.rd.foodorder.web.dto.service.MappingVendorDTOService;
import ua.rd.foodorder.web.dto.service.VendorDTOService;

@Service
public class VendorDTOServiceImpl implements VendorDTOService {

	private VendorService vendorService;

	private MappingVendorDTOService mappingService;

	@Override
	@Transactional(readOnly = true)
	public List<VendorDTO> findAll() {
		Iterable<Vendor> vendors = vendorService.findAll();
		List<VendorDTO> vendorDTOs = new ArrayList<>();
		for (Vendor vendor : vendors) {
			vendorDTOs.add(mappingService.convertToDto(vendor));
		}
		return vendorDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public VendorDTO findById(Long id) {
		Vendor vendor = vendorService.findById(id);
		return mappingService.convertToDto(vendor);
	}

	@Override
	@Transactional
	public VendorDTO update(VendorDTO vendorDTO) {
		Vendor vendor = mappingService.convertToEntity(vendorDTO);
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
	public VendorDTO save(VendorDTO vendorDTO) {
		Vendor vendor = mappingService.convertToEntity(vendorDTO);
		vendor = vendorService.save(vendor);
		return mappingService.convertToDto(vendor);
	}

	public VendorService getVendorService() {
		return vendorService;
	}

	@Autowired
	public void setVendorService(VendorService vendorService) {
		this.vendorService = vendorService;
	}

	public MappingVendorDTOService getMappingService() {
		return mappingService;
	}

	@Autowired
	public void setMappingService(MappingVendorDTOService mappingService) {
		this.mappingService = mappingService;
	}
}
