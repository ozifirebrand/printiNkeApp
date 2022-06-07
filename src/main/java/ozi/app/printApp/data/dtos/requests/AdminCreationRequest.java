package ozi.app.printApp.data.dtos.requests;

import lombok.Data;

@Data
public class AdminCreationRequest {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;
}
