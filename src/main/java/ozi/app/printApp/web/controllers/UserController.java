package ozi.app.printApp.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozi.app.printApp.data.dtos.requests.UserCreationRequest;
import ozi.app.printApp.services.userService.UserServices;

@Slf4j
@RestController
@RequestMapping("api/user/print")
public class UserController {
    @Autowired
    private UserServices userServices;


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


