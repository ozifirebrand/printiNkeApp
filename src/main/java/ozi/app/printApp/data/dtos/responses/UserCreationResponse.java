package ozi.app.printApp.data.dtos.responses;

import lombok.Data;
import ozi.app.printApp.data.models.PrintOrder;
import ozi.app.printApp.data.models.Role;

import java.util.List;

@Data
public class UserCreationResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String id;
    private Role role;
    private List<PrintOrder> orders;
}
