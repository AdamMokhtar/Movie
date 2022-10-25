package com.movie.movieapi.service;

import com.movie.movieapi.dto.MovieDto;

import java.util.List;

public interface MovieService {

    public List<MovieDto> getAllMovies();
}
