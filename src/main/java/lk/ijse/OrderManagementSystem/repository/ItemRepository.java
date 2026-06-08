package lk.ijse.OrderManagementSystem.repository;

import lk.ijse.OrderManagementSystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}