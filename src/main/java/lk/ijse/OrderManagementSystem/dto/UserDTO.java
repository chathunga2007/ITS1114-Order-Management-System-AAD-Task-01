package lk.ijse.OrderManagementSystem.dto;

import lk.ijse.OrderManagementSystem.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private UserStatus role;
}
