package com.movie.movieapi.controller;

import com.movie.movieapi.dto.LoginDto;
import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.dto.UserDto;
import com.movie.movieapi.service.RoleService;
import com.movie.movieapi.service.UserService;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import javax.naming.NameAlreadyBoundException;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping(Navigation.USER)
@Slf4j
public class UserController implements UserControllerInterface{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

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
            return new ResponseEntity("Name already exist",HttpStatus.BAD_REQUEST);
        }catch (ConstraintViolationException e) {
            return new ResponseEntity("constraint violation exception",HttpStatus.BAD_REQUEST);
        }
    }
    //TODO:: Login endpoint
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUserName(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }

}
