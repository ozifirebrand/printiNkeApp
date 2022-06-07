package ozi.app.printApp.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printApp.data.models.PrintAdmin;

public interface AdminRepository extends JpaRepository<PrintAdmin, String> {
    PrintAdmin findByEmail(String email);
}
