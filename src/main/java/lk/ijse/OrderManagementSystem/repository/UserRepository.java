package lk.ijse.OrderManagementSystem.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import lk.ijse.OrderManagementSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE role='CUSTOMER'", nativeQuery = true)
    List<User> getAllCustomer();

    @Query(value = "SELECT * FROM user WHERE role <> 'CUSTOMER'", nativeQuery = true)
    List<User> getAllEmployee();

    @Query(value = "SELECT u FROM User u WHERE u.role='CUSTOMER'")
    List<User> getAllCustomerJPQL();

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}