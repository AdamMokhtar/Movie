package com.movie.movieapi.service;

import com.movie.movieapi.dto.MovieDto;

import java.util.List;

public interface MovieService {

    public List<MovieDto> getAllMovies();

    public List<MovieDto> getMoviesByName(String name);

    public MovieDto postMovie(MovieDto movieDto);
}
