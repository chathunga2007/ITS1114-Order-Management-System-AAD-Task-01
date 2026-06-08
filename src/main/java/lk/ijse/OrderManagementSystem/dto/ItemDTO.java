package lk.ijse.OrderManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long itemId;
    private String itemName;
    private String itemQTY;
    private String itemPrice;
}
