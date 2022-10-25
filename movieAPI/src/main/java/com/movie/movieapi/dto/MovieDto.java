package com.movie.movieapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDto {

    private Long movieid;
    private String movieName;
    private double boxOfficeRate;
    private int releaseYear;
    private String language;
    private String rated;
}
