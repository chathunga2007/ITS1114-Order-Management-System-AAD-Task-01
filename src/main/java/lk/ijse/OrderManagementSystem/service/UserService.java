package lk.ijse.OrderManagementSystem.service;

import lk.ijse.OrderManagementSystem.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    List<UserDTO> getUserDetails();
}
