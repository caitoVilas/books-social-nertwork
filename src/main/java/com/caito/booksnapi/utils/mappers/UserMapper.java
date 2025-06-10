package com.caito.booksnapi.utils.mappers;

import com.caito.booksnapi.api.models.requests.RegistrationRequest;
import com.caito.booksnapi.api.models.responses.UserResponse;
import com.caito.booksnapi.persistence.entities.UserApp;

/**
 * Mapper class to convert RegistrationRequest to UserApp entity.
 *
 * @author caito
 *
 */
public class UserMapper {

    /**
     * Maps a RegistrationRequest to a UserApp entity.
     * param request RegistrationRequest
     * @return UserApp
     */
     public static UserApp mapToEntity(RegistrationRequest request) {
        return UserApp.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .password(request.getPassword()) // Password should be encoded before saving
                .build();
     }

    /**
     * Maps a UserApp entity to a UserResponse DTO.
     * param user UserApp
     * @return UserResponse
     */
     public static UserResponse mapToDto(UserApp user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .birthdate(user.getBirthdate())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(RoleMapper::mapToDto).toList())
                .build();
     }
}
