package com.movie.movieapi.controller;

import com.movie.movieapi.dto.CsvDto;
import com.movie.movieapi.dto.MovieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CsvControllerInterface {
    @Operation(summary = "Get all movies that were nominated for the best picture award", description = "Get all nominated best picture movies", tags = { "CSV" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CsvDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    List<CsvDto> getBestPicNom();
    @Operation(summary = "Get all movies that won for the best picture award", description = "Get all best picture movie winners", tags = { "CSV" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CsvDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    List<CsvDto> getBestPicWin();

    @Operation (summary = "Check if the input movie name was a winner for best picture movie award", description = "Check if the movie name won the best picture award", tags = { "CSV" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CsvDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
    CsvDto checkBestPicWin(@PathVariable("movieName")
                           String movieName);
}
