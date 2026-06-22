package lk.ijse.OrderManagementSystem.repository;

import lk.ijse.OrderManagementSystem.dto.ItemDTO;
import lk.ijse.OrderManagementSystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query(value = """
    SELECT *
    FROM item i
    WHERE (?1 IS NULL OR i.item_name LIKE CONCAT('%', ?1, '%'))
    """, nativeQuery = true)
    List<Item> filterItems(String itemName);

    @Query(value = "SELECT new lk.ijse.OrderManagementSystem.dto.ItemDTO(i.itemId, i.itemName, i.itemPrice, i.itemQTY) " +
            "FROM Item i " +
            "WHERE (?1 IS NULL OR i.itemName LIKE CONCAT('%', ?1, '%'))")
    List<ItemDTO> findByItemJPQL(String itemName);

    @Query(value = "SELECT i " +
            "FROM OrderItem oi " +
            "JOIN Item i ON oi.item = i " +
            "JOIN Order o ON oi.orders = o " +
            "WHERE o.orderId = ?1")
    List<Item> getItemsByOrder(long orderId);

    @Query(value = "SELECT oi.item " +
            "FROM OrderItem oi " +
            "WHERE oi.orders.orderId = ?1")
    List<Item> getItemsByOrder2(long orderId);
}