package ua.rd.foodorder.web.dto.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.service.UserService;
import ua.rd.foodorder.web.dto.domain.UserDTO;
import ua.rd.foodorder.web.dto.service.UserDTOService;

@Service
@Transactional
public class UserDTOServiceImpl implements UserDTOService {

    private static final String SORT_BY_FIELD = "name";

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

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public Page<UserDTO> getPageOfUserDTOs(Integer pageNumber, Integer size) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, size, Sort.Direction.ASC, SORT_BY_FIELD);
        Page<User> pageOfUser = userService.getPageOfUsers(pageRequest);
        return convertUserPageToUserDTOPage(pageRequest, pageOfUser);
    }

    private Page<UserDTO> convertUserPageToUserDTOPage(PageRequest pageRequest, Page<User> pageOfUser) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : pageOfUser.getContent()) {
            usersDTO.add(convertToDTO(user));
        }
        return new PageImpl<>(usersDTO, pageRequest, pageOfUser.getTotalElements());
    }
}
