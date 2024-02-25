package org.example.security_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = {"password"})
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Username shouldn`t be empty")
    @Size(min = 4, max = 30, message = "Username should be 4-30 characters long")
    private String username;
    @NotEmpty(message = "Password shouldn`t be empty")
    @Size(min = 4, message = "Password should be 4+ characters long")
    private String password;
    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Year of birth should be more than 1900")
    private int yearOfBirth;
    private String role;
    @Column(name = "locked")
    private boolean isLocked;
}
