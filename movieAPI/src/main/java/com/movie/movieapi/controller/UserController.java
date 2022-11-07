package com.movie.movieapi.controller;

import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.dto.UserDto;
import com.movie.movieapi.service.RoleService;
import com.movie.movieapi.service.UserService;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameAlreadyBoundException;

@RestController
@RequestMapping(Navigation.USER)
@Slf4j
public class UserController implements UserControllerInterface{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     *
     * @param userDto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Starting createUser method");
        try {
            RoleDto roleDto =  roleService.GetRole("Normal");
            return new ResponseEntity<>(userService.postUser(userDto,roleDto), HttpStatus.CREATED);
        } catch (NameAlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO:: Login endpoint

}
