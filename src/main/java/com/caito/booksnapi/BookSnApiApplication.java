package com.caito.booksnapi;

import com.caito.booksnapi.persistence.entities.Role;
import com.caito.booksnapi.persistence.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BookSnApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSnApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()){
                roleRepository.save(Role.builder()
                        .name("ROLE_ADMIN")
                        .build());
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(Role.builder()
                        .name("ROLE_USER")
                        .build());
            }
        };
    }

}
