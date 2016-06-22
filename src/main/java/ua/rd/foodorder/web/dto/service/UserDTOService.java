package ua.rd.foodorder.web.dto.service;

import ua.rd.foodorder.web.dto.domain.UserDTO;

public interface UserDTOService {

    Iterable<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO update(UserDTO userDTO);

    void remove(Long id);

    UserDTO save(UserDTO userDTO);
}
