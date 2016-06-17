package ua.rd.foodorder.web.dto.service;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.web.dto.domain.UserDTO;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */
public interface UserDTOService {

    Iterable<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO update(UserDTO userDTO);

    void remove(Long id);

    UserDTO save(UserDTO userDTO);
}
