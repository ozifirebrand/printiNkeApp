package ozi.app.printApp.data.dtos.requests;

import lombok.Data;

@Data
public class OrderCreationRequest {

    private String imageUrl;

    private double size;

    private int quantity;

    private String userId;
}
