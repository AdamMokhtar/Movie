package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovieControllerInterface {

    @Operation(summary = "Get all movies", description = "Get All the movies to view", tags = { "Movie" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<List<MovieDto>> getAllRecipes();
}
