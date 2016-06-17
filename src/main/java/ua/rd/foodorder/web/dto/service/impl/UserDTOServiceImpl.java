package ua.rd.foodorder.web.dto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.service.UserService;
import ua.rd.foodorder.web.dto.domain.UserDTO;
import ua.rd.foodorder.web.dto.service.MappingUserDTOService;
import ua.rd.foodorder.web.dto.service.UserDTOService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */
@Transactional
@Service
public class UserDTOServiceImpl implements UserDTOService {

    private UserService userService;

    private MappingUserDTOService mappingService;

    @Override
    public Iterable<UserDTO> findAll() {
        Iterable<User> users = userService.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(mappingService.convertToDTO(user));
        }
        return userDTOs;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return mappingService.convertToDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = mappingService.convertToUser(userDTO);
        user = userService.update(user);
        return mappingService.convertToDTO(user);
    }

    @Override
    public void remove(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = mappingService.convertToUser(userDTO);
        user = userService.save(user);
        return mappingService.convertToDTO(user);
    }


    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MappingUserDTOService getMappingService() {
        return mappingService;
    }

    @Autowired
    public void setMappingService(MappingUserDTOService mappingService) {
        this.mappingService = mappingService;
    }


}
