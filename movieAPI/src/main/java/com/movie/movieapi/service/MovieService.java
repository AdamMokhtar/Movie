package com.movie.movieapi.service;

import com.movie.movieapi.dto.MovieDto;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.NameAlreadyBoundException;
import java.util.List;

public interface MovieService {

    public List<MovieDto> getAllMovies();

    public List<MovieDto> getMoviesByName(String name);

    public MovieDto postMovie(MovieDto movieDto) throws NameAlreadyBoundException, InstanceAlreadyExistsException;

    public boolean checkSimilarMovieExist(MovieDto movieDto);
    public void deleteMovie(Long id);
}
