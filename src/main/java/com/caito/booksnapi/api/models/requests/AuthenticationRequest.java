package com.caito.booksnapi.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a request for user authentication.
 * Contains the user's email and password.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class AuthenticationRequest implements Serializable {
    private String email;
    private String password;
}
