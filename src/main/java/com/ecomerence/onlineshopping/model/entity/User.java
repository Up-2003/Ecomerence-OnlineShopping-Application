package com.ecomerence.onlineshopping.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,unique = true,length = 30)
    private String email;
    @Column(nullable = false,unique = true,length = 30)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;
    @Column(length = 100)
    private String address;

    @ElementCollection
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles= new HashSet<>();

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

}
