package com.esterxie.flightmanagementsystem;

import com.esterxie.flightmanagementsystem.model.User;
import com.esterxie.flightmanagementsystem.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class FlightManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> admin = userRepository.findUserByEmail("admin@gmail.com");
            if (!admin.isPresent()) {
                User newAdmin = User.builder()
                        .name("Admin Account")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("12345"))
                        .role("ROLE_ADMIN")
                        .build();
                userRepository.save(newAdmin);
            }
        };
    }
}
