package ua.rd.foodorder.web.dto.service;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.web.dto.domain.UserDTO;

import java.util.Set;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */
public class MappingUserDTOService {

    private ModelMapper modelMapper;

    public UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public void setConverters(Set<AbstractConverter> converters) {
        this.converters = converters;
        for (AbstractConverter converter : converters) {
            modelMapper.addConverter(converter);
        }
    }

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
}
