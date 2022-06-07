package ozi.app.printApp.data.dtos.responses;

import lombok.Data;
import ozi.app.printApp.data.models.Role;

@Data
public class AdminCreationResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;
}
