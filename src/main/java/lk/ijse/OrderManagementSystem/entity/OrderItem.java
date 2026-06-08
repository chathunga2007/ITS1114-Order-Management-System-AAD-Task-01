package lk.ijse.OrderManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private int orderItemQTY;
    private int orderItemPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    private Order orders;

    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
}