package ozi.app.printApp.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")

public class HomeController {
    @GetMapping("")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok().body("Welcome to printApp");
    }
}
