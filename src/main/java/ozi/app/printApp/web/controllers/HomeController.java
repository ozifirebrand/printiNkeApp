package ozi.app.printApp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.services.orderService.OrderServices;

@RestController
@RequestMapping("/")

public class HomeController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping("")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok().body("Welcome to printApp");
    }
    @GetMapping("/welcome-page")
    public OrderCreationResponse makeOrder(@RequestBody OrderCreationRequest request) throws BusinessLogicException {
        return orderServices.createOrder(request);
    }
}
