package lk.ijse.OrderManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.OrderManagementSystem.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;

    @Enumerated(EnumType.STRING)
    private UserStatus role;
}