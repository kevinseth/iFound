package com.iFound.repository;

import com.iFound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username); // optional

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
