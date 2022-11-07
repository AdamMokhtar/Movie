package com.movie.movieapi.controller;

import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Navigation.ROLE)
@Slf4j
public class RoleController implements RoleControllerInterface{
    @Override
    public ResponseEntity<List<RoleDto>> getRoleByName() {
        return null;
    }
}
