package com.caito.booksnapi.api.controllers.impl;

import com.caito.booksnapi.api.controllers.contracts.AuthController;
import com.caito.booksnapi.api.models.requests.AuthenticationRequest;
import com.caito.booksnapi.api.models.requests.RegistrationRequest;
import com.caito.booksnapi.api.models.responses.AuthenticateResponse;
import com.caito.booksnapi.services.contracts.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caito Vilas
 * date: 08/2024
 * This class is used to handle authentication requests.
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService service;


    @Override
    public ResponseEntity<?> register(RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Override
    public ResponseEntity<?> activateAccount(String token) throws MessagingException {
        service.activateAccount(token);
        return ResponseEntity.ok().build();
    }
}
