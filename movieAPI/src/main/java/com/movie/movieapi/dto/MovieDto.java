package com.movie.movieapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class MovieDto {

    private Long movieid;
    @NotNull(message = "movie Name cannot be null")
    @NotBlank(message = "movie Name cannot be blank")
    private String movieName;
    private double boxOfficeRate;
    @NotNull(message = "release Year cannot be null")
    @Positive(message = "release Year cannot be negative")
    private int releaseYear;
    @NotNull(message = "language cannot be null")
    @NotBlank(message = "language Name cannot be blank")
    private String language;
    private String rated;
}
