package ozi.app.printApp.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.requests.UserCreationRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.services.userService.UserServices;

@Slf4j
@RestController
@RequestMapping("api/user/print")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest orderCreationRequest){
        try {
            OrderCreationResponse orderCreationResponse = userServices.createOrder(orderCreationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderCreationResponse);
        }
        catch (BusinessLogicException businessLogicException){
            return ResponseEntity.badRequest().body(businessLogicException.getMessage());
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id){
        try {
            OrderCreationResponse orderCreationResponse = userServices.getOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(orderCreationResponse);
        }
        catch (BusinessLogicException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/orders{id}")
    public ResponseEntity<?> viewAllOrders(@PathVariable String id){

        return null;
    }

        @GetMapping("/profile/{id}")
    public ResponseEntity<?> viewProfileDetails(@PathVariable String id){

        return null;
    }

    @PatchMapping("/profile/edit")
    public ResponseEntity<?> editProfileDetails(@RequestParam String id, UserCreationRequest request){

        return null;
    }
}


