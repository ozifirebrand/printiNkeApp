package ozi.app.printApp.data.dtos.requests;

import lombok.Data;

@Data
public class OrderUpdateRequest {

    private double size;

    private int quantity;
}
