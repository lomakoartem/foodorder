package ua.rd.foodorder.web.dto.service;

import org.springframework.data.domain.Page;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.web.dto.domain.UserDTO;

public interface UserDTOService {

    Iterable<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO update(UserDTO userDTO);

    void remove(Long id);

    UserDTO save(UserDTO userDTO);
    
    Page<UserDTO> getPageOfUserDTOs(Integer pageNumber, Integer size);
}
