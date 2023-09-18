package com.workintech.twitterApp;

import com.workintech.twitterApp.entity.Role;
import com.workintech.twitterApp.repository.RoleRepository;
import com.workintech.twitterApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TwitterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        return args -> {

            if (roleRepository.findByAuthority("ADMIN").isPresent()) {
                return;
            }

            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");

            Role userRole = new Role();
            userRole.setAuthority("USER");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(adminRole);
        };
    }
}