package ua.rd.foodorder.web.dto.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.service.UserService;
import ua.rd.foodorder.web.dto.domain.UserDTO;
import ua.rd.foodorder.web.dto.service.UserDTOService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */

@Service
@Transactional
public class UserDTOServiceImpl implements UserDTOService {

    private UserService userService;

    private ModelMapper modelMapper;

    @Override
    public Iterable<UserDTO> findAll() {
        Iterable<User> users = userService.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(convertToDTO(user));
        }
        return userDTOs;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return convertToDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        user = userService.update(user);
        return convertToDTO(user);
    }

    @Override
    public void remove(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        user = userService.save(user);
        return convertToDTO(user);
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
