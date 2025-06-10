package com.caito.booksnapi.utils.mappers;

import com.caito.booksnapi.api.models.responses.RoleResponse;
import com.caito.booksnapi.persistence.entities.Role;

/**
 * Mapper class to convert Role entity to RoleResponse DTO.
 *
 * @author caito
 *
 */
public class RoleMapper {

    /**
     * Maps a Role entity to a RoleResponse DTO.
     *
     * @param role the Role entity to map
     * @return a RoleResponse DTO containing the role name
     */
    public static RoleResponse mapToDto(Role role) {
        return  RoleResponse.builder()
                .name(role.getName())
                .build();
    }
}
