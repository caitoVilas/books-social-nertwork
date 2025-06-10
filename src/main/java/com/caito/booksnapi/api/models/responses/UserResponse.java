package com.caito.booksnapi.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response model for user details.
 *
 * @author caito
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private List<RoleResponse> roles;
}
