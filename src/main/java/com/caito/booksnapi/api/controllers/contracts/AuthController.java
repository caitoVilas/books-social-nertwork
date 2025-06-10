package com.caito.booksnapi.api.controllers.contracts;

import com.caito.booksnapi.api.models.requests.AuthenticationRequest;
import com.caito.booksnapi.api.models.requests.RegistrationRequest;
import com.caito.booksnapi.api.models.responses.AuthenticateResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author caito Vilas
 * date: 08/2024
 * This interface defines the contract for authentication-related operations.
 */
public interface AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) throws MessagingException;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticationRequest request);

    @GetMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestParam String token) throws MessagingException;
}
