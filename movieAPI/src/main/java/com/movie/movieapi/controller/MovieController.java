package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.service.MovieService;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * This method is used to display all the movies
     * All the movies with details will be returned as a list to select
     *
     * @return List<MovieDto>
     */
    @GetMapping(value="/allmovies")
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        log.info("Starting getAllMovies method {}");
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }


    /**
     * This method is used to display all the movies
     * All the movies with details will be returned as a list to select
     *
     * @return List<MovieDto>
     */
    @GetMapping(value="/movie")
    public ResponseEntity<List<MovieDto>> getMoviesByName(@RequestParam String name){
        log.info("Starting getMoviesByName method {}"+name);
        return new ResponseEntity<>(movieService.getMoviesByName(name), HttpStatus.OK);
    }

    //check name year and language
    @PostMapping(value="/movie")
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        log.info("Starting createMovie method");
        return new ResponseEntity<>(movieService.postMovie(movieDto), HttpStatus.CREATED);
    }


}
