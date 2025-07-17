package com.example.springdata.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
