package ua.rd.foodorder.web.dto.service;

import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.domain.VendorDto;

public class MappingVendorDtoService {

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
	
	public VendorDto convertToDto(Vendor vendor){
		return modelMapper.map(vendor, VendorDto.class);
	}
	
	public Vendor convertToEntity(VendorDto vendorDto){
		return modelMapper.map(vendorDto, Vendor.class);
	}
	
}
