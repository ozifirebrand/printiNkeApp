package ozi.app.printApp.data.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
public class PrintUser {
    @Id
    @GeneratedValue(generator= "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Role role;

    @OneToMany
    @Fetch(FetchMode.JOIN)
    private List<PrintOrder> orders;
}
