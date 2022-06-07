package ozi.app.printApp.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printApp.data.models.OrderStatus;
import ozi.app.printApp.data.models.PrintOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<PrintOrder, String> {
    List<PrintOrder> findByOrderDate(LocalDateTime date);

    List<PrintOrder> findByOrderStatus(OrderStatus status);

    List<PrintOrder> findPrintOrderByUserId(String userId);
}
