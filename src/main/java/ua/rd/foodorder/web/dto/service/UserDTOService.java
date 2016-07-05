package ua.rd.foodorder.web.dto.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.web.dto.domain.UserDTO;

public interface UserDTOService {

    Iterable<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO update(UserDTO userDTO);

    void remove(Long id);

    UserDTO save(UserDTO userDTO);

    Page<UserDTO> getPageOfUserDTOs(Integer pageNumber, Integer size);

    public Page<UserDTO> searchPageOfUserDTOs(String searchTerm, Integer pageNumber, Integer size);

    void saveUsersFromFile(MultipartFile file);
    
    public Iterable<UserDTO> searchAllOfUserDTOs(String searchTerm);
}

