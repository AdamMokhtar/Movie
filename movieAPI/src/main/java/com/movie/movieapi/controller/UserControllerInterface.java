package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerInterface {

    @Operation(summary = "Post a User", description = "Create a new user", tags = { "User" }, responses = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);
}
