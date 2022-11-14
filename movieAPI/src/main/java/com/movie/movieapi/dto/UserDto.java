package com.movie.movieapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Data
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
    @JsonIgnore
    private Set<RoleDto> roles = new HashSet<>();
}
