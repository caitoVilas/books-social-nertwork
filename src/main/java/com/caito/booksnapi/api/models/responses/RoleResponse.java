package com.caito.booksnapi.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a response containing role information.
 * This class is used to encapsulate the details of a role in the system.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class RoleResponse implements Serializable {
    private String name;
}
