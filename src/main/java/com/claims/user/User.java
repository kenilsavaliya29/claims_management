package com.claims.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity()
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @NotEmpty
    @Column(unique = true)
    private String email;
    private String password;

}
