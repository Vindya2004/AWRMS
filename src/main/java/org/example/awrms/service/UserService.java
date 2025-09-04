package org.example.awrms.service;

import org.example.awrms.dto.UserDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    boolean deleteUserByEmail(String email);

    boolean updateUserRole(String email, String role);
}
