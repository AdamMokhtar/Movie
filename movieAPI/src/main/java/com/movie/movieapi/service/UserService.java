package com.movie.movieapi.service;

import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.dto.UserDto;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

public interface UserService {

    public UserDto postUser(UserDto userDto, RoleDto roleDto) throws NameAlreadyBoundException;
    public List<UserDto> getAllUsers();
    public boolean checkUserNameInDB(UserDto userDto);
}
