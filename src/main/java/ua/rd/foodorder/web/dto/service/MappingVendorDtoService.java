package ua.rd.foodorder.web.dto.service;

import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.domain.VendorDTO;

public class MappingVendorDTOService {

	private ModelMapper modelMapper;
	
	private Set<AbstractConverter> converters;

	
	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Set<AbstractConverter> getConverters() {
		return converters;
	}

	public void setConverters(Set<AbstractConverter> converters) {
		this.converters = converters;
		for (AbstractConverter converter : converters){
			modelMapper.addConverter(converter);
		}
	}
	
	public VendorDTO convertToDto(Vendor vendor){
		return modelMapper.map(vendor, VendorDTO.class);
	}
	
	public Vendor convertToEntity(VendorDTO vendorDTO){
		return modelMapper.map(vendorDTO, Vendor.class);
	}
}
