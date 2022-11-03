package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieControllerInterface {

    @Operation(summary = "Get all movies", description = "Get All the movies to view", tags = { "Movie" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<List<MovieDto>> getAllMovies();


    @Operation(summary = "Get movie", description = "Get All the details of movies by name", tags = { "Movie" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<List<MovieDto>> getMoviesByName(@RequestParam String name);

    @Operation(summary = "Create movie", description = "Create a new Movie", tags = { "Movie" }, responses = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto);

    @Operation(summary = "Delete movie", description = "Delete a movie by Id", tags = { "Movie" }, responses = {
            @ApiResponse(description = "No content", responseCode = "204"),
            @ApiResponse(description = "Not found", responseCode = "404"),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    ResponseEntity<Void> deleteMovie(@RequestParam Long id);

}
