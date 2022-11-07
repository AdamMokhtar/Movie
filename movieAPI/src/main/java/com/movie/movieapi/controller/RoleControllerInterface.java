package com.movie.movieapi.controller;

import com.movie.movieapi.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleControllerInterface {
    @Operation(summary = "Get role by name", description = "Get a role by its name", tags = { "Role" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<List<RoleDto>> getRoleByName();
}
