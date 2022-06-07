package ozi.app.printApp.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printApp.data.models.PrintUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PrintUser, String> {
    Optional<PrintUser> findPrintUserByEmail(String email);

}