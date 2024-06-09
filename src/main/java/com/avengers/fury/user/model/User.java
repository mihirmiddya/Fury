package com.avengers.fury.user.model;

import com.avengers.fury.user.dto.CreateUserForm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "tbluser")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public User(CreateUserForm form) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.name = form.getName();
        this.email = form.getEmail();
        this.password = passwordEncoder.encode(form.getPassword());
        this.createdOn = LocalDateTime.now();
    }
}
