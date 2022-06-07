package ozi.app.printApp.data.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PrintAdmin {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(strategy="uuid", name="system-uuid")
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(unique= true)
    private String email;

    private Role role;

    private String password;
}
