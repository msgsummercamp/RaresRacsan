package com.example.springdata.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Column(nullable = false)
    private String email;

    private String firstName;

    private String lastName;
}
