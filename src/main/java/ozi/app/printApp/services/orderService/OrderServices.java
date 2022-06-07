package ozi.app.printApp.services.orderService;

import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.requests.OrderUpdateRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.data.models.OrderStatus;
import ozi.app.printApp.data.models.PrintOrder;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.exceptions.OrderException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServices {

    OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogicException;
    boolean deleteAllOrders() throws OrderException;
    boolean deleteOrderById(String id) throws BusinessLogicException;
    boolean deleteOrderByUserId(String userId) ;
    OrderCreationResponse updateOrderDetails(String orderId, OrderUpdateRequest request) throws BusinessLogicException;
    OrderCreationResponse updateOrderStatus(String orderId, OrderStatus status) throws BusinessLogicException;
    OrderCreationResponse updateOrderDeliverDate(String orderId, LocalDateTime date) throws BusinessLogicException;
    OrderCreationResponse getOrderById(String id) throws OrderException;
    List<PrintOrder> getAllOrders() throws OrderException;
    List<PrintOrder> getOrdersByDate(LocalDateTime dateTime) throws BusinessLogicException;
    List<PrintOrder> getOrdersByStatus(OrderStatus status) throws BusinessLogicException;
    List<PrintOrder> getOrdersByUserId(String userId) throws OrderException;
}
