package com.iFound.service;

import com.iFound.dto.UserRegistrationDto;
import com.iFound.model.User;
import com.iFound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public User register(UserRegistrationDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        LocalDate dob = null;
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().isEmpty()) {
            try {
                dob = LocalDate.parse(dto.getDateOfBirth());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date of birth format");
            }
        }

        User.Gender genderEnum = null;
        if (dto.getGender() != null && !dto.getGender().isEmpty()) {
            try {
                genderEnum = User.Gender.valueOf(dto.getGender().toUpperCase());
            } catch (IllegalArgumentException ignored) {
                genderEnum = User.Gender.OTHER;
            }
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setTelephone(dto.getTelephone());
        user.setPosition(dto.getPosition());
        user.setGender(genderEnum);
        user.setDateOfBirth(dob);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setRegistrationDate(LocalDate.now());

        return repository.save(user);
    }
}
