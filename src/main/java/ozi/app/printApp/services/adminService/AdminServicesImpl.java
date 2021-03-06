package ozi.app.printApp.services.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printApp.data.dtos.requests.AdminCreationRequest;
import ozi.app.printApp.data.dtos.responses.AdminCreationResponse;
import ozi.app.printApp.data.models.PrintAdmin;
import ozi.app.printApp.data.repositories.AdminRepository;
import ozi.app.printApp.exceptions.AdminException;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImpl implements AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminCreationResponse createAdmin(AdminCreationRequest request)
            throws BusinessLogicException {
        validate(request);
        PrintAdmin admin = Mapper.map(request);
        admin.setPhoneNumber(request.getPhoneNumber());
        PrintAdmin savedAdmin = adminRepository.save(admin);
        return Mapper.map(savedAdmin);
    }

    private void validate(AdminCreationRequest request) throws BusinessLogicException {
        boolean firstNameIsEmpty= request.getFirstName() == null;
        boolean lastNameIsEmpty= request.getLastName() == null;
        boolean emailIsEmpty= request.getEmail() == null;
        boolean phoneNumberIsEmpty= request.getPhoneNumber() == null;
        boolean passwordIsEmpty= request.getPassword() == null;

        if ( firstNameIsEmpty||lastNameIsEmpty||emailIsEmpty||phoneNumberIsEmpty||passwordIsEmpty)
            throw new AdminException("These details are incomplete");
    }

    @Override
    public PrintAdmin getAdminById(String id) throws BusinessLogicException {
        Optional<PrintAdmin> optionalPrintAdmin = adminRepository.findById(id);
        if ( optionalPrintAdmin.isEmpty() )
            throw new AdminException("No such user with id "+id +" exists!");
        return optionalPrintAdmin.get();
    }

    @Override
    public PrintAdmin getAdminByEmail(String email) throws BusinessLogicException{
        PrintAdmin admin = adminRepository.findByEmail(email);
        if ( admin == null ) throw new AdminException("No such user with email "+ email +" exists!");
        return admin ;
    }

    @Override
    public List<PrintAdmin> getAllAdmins() throws AdminException {
        List<PrintAdmin> admins = adminRepository.findAll();
        if (admins.size()==0  ) throw new AdminException("There are no admins here!");
        return admins;
    }

    @Override
    public boolean deleteAdminById(String id) {
        adminRepository.deleteById(id);
        final boolean empty = adminRepository.findById(id).isEmpty();
        return empty;
    }

    @Override
    public boolean deleteAllAdmins() throws AdminException {
        if ( adminRepository.findAll().size()==0 )
            throw new AdminException("There are no admins to delete!");
        adminRepository.deleteAll();
        return adminRepository.findAll().isEmpty();
    }
}
