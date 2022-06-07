package ozi.app.printApp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.services.orderService.OrderServices;

@RestController
@RequestMapping("/home")

public class HomeController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping("/welcome-page")
    public OrderCreationResponse makeOrder(@RequestBody OrderCreationRequest request) throws BusinessLogicException {
        return orderServices.createOrder(request);
    }
}
