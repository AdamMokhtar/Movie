package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.service.MovieService;
import com.movie.movieapi.utils.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
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
    @PreAuthorize("hasAuthority('Admin')")
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

    /**
     * This method is used to create a new movie
     *
     * @param movieDto
     * @return MovieDto
     * @exception MovieNotFound will be thrown if a movie with the same name, release year and language exist
     * @exception ConstraintViolationException will be thrown if a movies without the name or release year or language is submitted
     */
    @PostMapping(value="/movie")
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        log.info("Starting createMovie method");
        try {
            return new ResponseEntity<>(movieService.postMovie(movieDto), HttpStatus.CREATED);
        }catch (MovieNotFound mo){
            return new ResponseEntity("There is a similar movie in the database with the name "
                    + movieDto.getMovieName() + " ,release Year "+ movieDto.getReleaseYear() +
                    " ,and language " +movieDto.getLanguage(), HttpStatus.NOT_FOUND);

        }catch (ConstraintViolationException c){
            return new ResponseEntity("there was a ConstraintViolationException regarding the object of the movie",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *This method is used to delete a movie
     *
     * @param id
     * @return void
     * @exception EntityNotFoundException will be thrown if a movie id was null or not found in the database
     */
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping(value="/movie/{id}")
    public ResponseEntity<Void> deleteMovie(@RequestParam Long id) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("The movie id was not found",HttpStatus.NOT_FOUND);
        }
    }


}
