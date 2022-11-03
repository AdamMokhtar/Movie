package com.movie.movieapi.dto;

import com.movie.movieapi.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    @NotNull(message = "User Name cannot be null")
    @NotBlank(message = "User Name cannot be blank")
    private String username;
    //TODO:: Add more validations for password
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private Collection<Role> roles;
}
