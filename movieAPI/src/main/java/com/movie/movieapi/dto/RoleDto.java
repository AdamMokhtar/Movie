package com.movie.movieapi.dto;

import com.movie.movieapi.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
public class RoleDto {
    @NotNull(message = "User Name cannot be null")
    @NotBlank(message = "User Name cannot be blank")
    private Integer roleId;
    private String roleName;
    private Collection<User> users;
}
