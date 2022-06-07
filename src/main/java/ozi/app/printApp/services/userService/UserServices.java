package ozi.app.printApp.services.userService;

import ozi.app.printApp.data.dtos.requests.UserCreationRequest;
import ozi.app.printApp.data.dtos.responses.UserCreationResponse;
import ozi.app.printApp.data.models.PrintUser;
import ozi.app.printApp.data.models.Role;
import ozi.app.printApp.exceptions.BusinessLogicException;

import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request)
            throws BusinessLogicException;
    boolean deleteUserById(String userId);
    boolean deleteAllUsers() throws BusinessLogicException;
    void updateUserRole(String userId, Role role);
    List<PrintUser> getAllUsers();
    PrintUser getUserById(String userId) throws BusinessLogicException;
    PrintUser getUserByEmail(String email) throws BusinessLogicException;
}
