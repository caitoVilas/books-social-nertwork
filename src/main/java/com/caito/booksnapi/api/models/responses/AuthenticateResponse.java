package com.caito.booksnapi.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a response containing an authentication token.
 * This token is used to authenticate subsequent requests.
 *
 * @author caito
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class AuthenticateResponse implements Serializable {
    private String auth_token;
}
