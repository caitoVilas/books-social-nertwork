package com.caito.booksnapi.services.contracts;

import com.caito.booksnapi.api.models.requests.AuthenticationRequest;
import com.caito.booksnapi.api.models.requests.RegistrationRequest;
import com.caito.booksnapi.api.models.responses.AuthenticateResponse;
import jakarta.mail.MessagingException;

/**
 * @author caito Vilas
 * date: 08/2024
 * This interface defines the contract for authentication services.
 * It includes methods for user registration, authentication, and account activation.
 */
public interface AuthenticationService {

    void register(RegistrationRequest request) throws MessagingException;
    AuthenticateResponse authenticate(AuthenticationRequest request);
    void activateAccount(String token) throws MessagingException;
}
