package ozi.app.printApp.services.adminService;

import ozi.app.printApp.data.dtos.requests.AdminCreationRequest;
import ozi.app.printApp.data.dtos.responses.AdminCreationResponse;
import ozi.app.printApp.data.models.PrintAdmin;
import ozi.app.printApp.exceptions.BusinessLogicException;

import java.util.List;

public interface AdminServices {
    AdminCreationResponse createAdmin (AdminCreationRequest request) throws BusinessLogicException;
    PrintAdmin getAdminById(String id) throws BusinessLogicException;
    PrintAdmin getAdminByEmail(String email) throws BusinessLogicException;
    List<PrintAdmin> getAllAdmins() throws BusinessLogicException;
    boolean deleteAdminById(String id);
    boolean deleteAllAdmins() throws BusinessLogicException;
}
