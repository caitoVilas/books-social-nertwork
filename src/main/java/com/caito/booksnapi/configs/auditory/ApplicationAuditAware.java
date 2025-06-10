package com.caito.booksnapi.configs.auditory;

import com.caito.booksnapi.persistence.entities.UserApp;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of AuditorAware to provide the current auditor's ID.
 * It retrieves the ID of the authenticated user from the security context.
 * If no user is authenticated, it returns an empty Optional.
 *
 * @author caito
 *
 */
public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null ||
                !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        UserApp userPrincipal = (UserApp) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
