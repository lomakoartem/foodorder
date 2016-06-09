package ua.rd.foodorder.web.dto.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.service.VendorService;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.service.VendorDtoService;


@Service
public class VendorDtoServiceImpl implements VendorDtoService{

	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<VendorDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VendorDto findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VendorDto update(VendorDto vendorDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VendorDto save(VendorDto vendorDto) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
