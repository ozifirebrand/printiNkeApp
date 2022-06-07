package ozi.app.printApp.data.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ozi.app.printApp.data.models.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderCreationResponse {
    private String id;

    private String imageUrl;

    private double size;

    private int quantity;

    private double price;

    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime orderDate;

    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime deliveryDate;

    private final boolean ordered= true;

    private OrderStatus orderStatus;

}
