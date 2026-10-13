package com.ecomerence.onlineshopping.model.service;

import com.ecomerence.onlineshopping.dto.RegisterForm;
import com.ecomerence.onlineshopping.model.entity.Role;
import com.ecomerence.onlineshopping.model.entity.User;
import com.ecomerence.onlineshopping.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterForm form) {
        if (repo.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("Username taken");
        }
        if (repo.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email taken");
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFullName(form.getFullName());
        user.setRoles(Set.of(Role.USER));

        return repo.save(user);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
