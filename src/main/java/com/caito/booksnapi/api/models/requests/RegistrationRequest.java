package com.caito.booksnapi.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author caito Vilas
 * date: 08/2024
 * This class is used to represent a registration request.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class RegistrationRequest implements Serializable {
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String confirmPassword;
}
