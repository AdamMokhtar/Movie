package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.service.MovieService;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Navigation.MOVIE)
@Slf4j
public class MovieController implements MovieControllerInterface{

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * This method is used to display all the recipes
     * All the Recipes will be returned as a list to select
     *
     * @return List<recipes>
     */
    @GetMapping(value="/allmovies")
    public ResponseEntity<List<MovieDto>> getAllRecipes(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

}
