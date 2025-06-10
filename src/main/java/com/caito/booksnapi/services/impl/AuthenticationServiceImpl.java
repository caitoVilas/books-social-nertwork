package com.caito.booksnapi.services.impl;

import com.caito.booksnapi.api.exceptions.customs.BadRequestException;
import com.caito.booksnapi.api.exceptions.customs.NotFoundException;
import com.caito.booksnapi.api.models.requests.AuthenticationRequest;
import com.caito.booksnapi.api.models.requests.RegistrationRequest;
import com.caito.booksnapi.api.models.responses.AuthenticateResponse;
import com.caito.booksnapi.configs.security.JwtService;
import com.caito.booksnapi.persistence.entities.Token;
import com.caito.booksnapi.persistence.entities.UserApp;
import com.caito.booksnapi.persistence.repositories.RoleRepository;
import com.caito.booksnapi.persistence.repositories.TokenRepository;
import com.caito.booksnapi.persistence.repositories.UserRepository;
import com.caito.booksnapi.services.contracts.AuthenticationService;
import com.caito.booksnapi.services.contracts.EmailService;
import com.caito.booksnapi.services.helpers.ValidationHelper;
import com.caito.booksnapi.utils.enums.EmailTemplateName;
import com.caito.booksnapi.utils.logs.WriteLog;
import com.caito.booksnapi.utils.mappers.UserMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of the AuthenticationService interface.
 * Handles user registration and validation.
 *
 * @author caito
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationURL;

    /**
     * Registers a new user by validating the request and saving the user to the repository.
     * Sends a validation email to the user after registration.
     *
     * @param request the registration request containing user details
     */
    @Override
    @Transactional
    public void register(RegistrationRequest request) throws MessagingException {
        log.info(WriteLog.logInfo("Registering user service"));
        this.validateUser(request);
        var rol = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));
        var user = UserMapper.mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(rol));
        user.setAccontLocked(false);
        user.setEnabled(false);
        userRepository.save(user);
        sendValidationEmail(user);
    }

    /**
     * Authenticates a user based on the provided authentication request.
     * Currently, this method is not implemented and returns null.
     *
     * @param request the authentication request containing user credentials
     * @return an AuthenticateResponse object (currently null)
     */
    @Override
    @Transactional
    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        log.info(WriteLog.logInfo("Authenticating user service"));
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (UserApp) auth.getPrincipal();
        claims.put("fullname", user.fullname());
        var jwt = jwtService.generateToken(claims, user);
        return AuthenticateResponse.builder()
                .auth_token(jwt)
                .build();
    }

    /**
     * Activates a user account based on the provided token.
     * Validates the token and updates the user's status to enabled.
     * If the token is expired, sends a new validation email.
     *
     * @param token the activation token
     * @throws MessagingException if there is an error sending the email
     */
    @Override
    public void activateAccount(String token) throws MessagingException {
        log.info(WriteLog.logInfo("Activating user service"));
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token invalid"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            log.error(WriteLog.logError("Token expired"));
            sendValidationEmail(savedToken.getUser());
            throw new BadRequestException(List.of("Token expired, a new one has been sent to your email"));
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setEnabled(true);
        user.setAccontLocked(false);
        userRepository.save(user);
        savedToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    /**
     * Sends a validation email to the user after registration.
     * Generates a validation token and saves it.
     *
     * @param user the user to whom the validation email will be sent
     */
    private void sendValidationEmail(UserApp user) throws MessagingException {
        var newToken = generateAndSaveValidationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullname(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationURL,
                newToken,
                "Account Activation"
        );
        // todo: send email with the validation token
    }

    /**
     * Generates a validation token for the user and saves it.
     * The token is a random alphanumeric string of a specified length.
     *
     * @param user the user for whom the validation token is generated
     * @return the generated validation token
     */
    private String generateAndSaveValidationToken(UserApp user) {
        log.info(WriteLog.logInfo("Generating validation token for user: " + user.getEmail()));
        var generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    /**
     * Generates a random alphanumeric activation code of the specified length.
     *
     * @param lenght the length of the activation code to be generated
     * @return a randomly generated activation code as a String
     */
    private String generateActivationCode(int lenght) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < lenght; i++) {
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }
        return codeBuilder.toString();
    }

    /**
     * Validates the user registration request.
     * Checks for required fields, email format, password strength, and matching passwords.
     *
     * @param request the registration request to validate
     * @throws BadRequestException if validation fails
     */
    private void validateUser(RegistrationRequest request) {
        List<String> errors = new ArrayList<>();
        log.info(WriteLog.logInfo("Validating user..."));
        //Validate Firstname
        if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
            errors.add("First name is required");
        }
        //Validate Lastname
        if (request.getLastname() == null || request.getLastname().isEmpty()) {
            errors.add("Last name is required");
        }
        //Validate email
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            errors.add("Email is already in use");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Invalid email");
        }
        //Validate Password
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required");
        } else if (!ValidationHelper.validatePassword(request.getPassword())) {
            errors.add("Invalid password");
        } else if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.add("Passwords do not match");
        }

        if (!errors.isEmpty()) {
            log.error(WriteLog.logError("User validation failed: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }

    }
}
