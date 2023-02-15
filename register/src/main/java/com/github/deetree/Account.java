package com.github.deetree;

import jakarta.persistence.*;

/**
 * @author Mariusz Bal
 */
@Entity
class Account {
    @Column(nullable = false)
    private final String name;
    @Column(unique = true, nullable = false)
    private final String email;
    @Column(nullable = false)
    private final String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    Account(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
