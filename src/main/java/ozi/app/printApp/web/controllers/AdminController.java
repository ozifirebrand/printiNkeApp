package ozi.app.printApp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.services.adminService.AdminServices;
import ozi.app.printApp.services.orderService.OrderServices;

@RestController
@RequestMapping("/api/admin/print")
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest orderCreationRequest){
        try {
            OrderCreationResponse orderCreationResponse = orderServices.createOrder(orderCreationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderCreationResponse);
        }
        catch (BusinessLogicException businessLogicException){
            return ResponseEntity.badRequest().body(businessLogicException.getMessage());
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id){
        try {
            OrderCreationResponse orderCreationResponse = orderServices.getOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(orderCreationResponse);
        }
        catch (BusinessLogicException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}