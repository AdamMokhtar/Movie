package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.entity.Movie;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.mapper.MovieMapper;
import com.movie.movieapi.repository.MovieRepository;
import com.movie.movieapi.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    private Validator validator;

    /**
     * Creates a MovieServiceImpl with a MovieRepository, MovieMapper and a Validator
     *
     * @param movieRepository is the connection layer with database
     * @param movieMapper is used to convert from entity to Dto and the other way around
     * @param validator is used to check validations applied
     */
    public MovieServiceImpl(MovieRepository movieRepository,MovieMapper movieMapper,Validator validator) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.validator = validator;
       }

    /**
     * Collects all the movies found in the database
     *
     * @should callFindAllMethodFromMovieRepo
     * @should throwMovieNotFoundExceptionWhenNoMovieWasFound
     * @should returnAListOfMovies
     *
     * @return a list of MovieDto that were found in the database
     * @throws MovieNotFound when the list of movies returned is empty
     */
    @Override
    public List<MovieDto> getAllMovies() {
        log.info("Entering the getAllMovies of MovieServiceImpl class!");
        List<Movie> movieList = movieRepository.findAll();
        if(movieList.size() < 1)
            throw new MovieNotFound("There were no movies found");
        return movieMapper.toDto(movieList);
    }

    /**
     * Collects all the movies found in the database similar to the name passed
     *
     * @should throwIllegalArgumentExceptionWhenNameIsNullOrEmpty
     * @should callFindByMovieName
     * @should throwMovieNotFoundExceptionWhenNoMovieWasFound
     * @should returnAListOfMoviesFound
     *
     * @param name is the name of the movie to be found
     * @return a list of MovieDto similar to the name passed
     * @throws IllegalArgumentException when name is null or empty
     * @throws MovieNotFound when the list of movies returned is empty
     */
    @Override
    public List<MovieDto> getMoviesByName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("name cannot be null or empty");

        List<Movie> movieList = movieRepository.findByMovieName(name);
        if(movieList.size() < 1)
            throw new MovieNotFound("No movie/s with the name "+name);
        return movieMapper.toDto(movieList);
    }

    /**
     * Post a movie to the database
     *
     * @should callSaveFromMovieRepo
     * @should throwInstanceAlreadyExistsExceptionWhenThereIsAlreadySimilarMovie
     * @should throwConstraintViolationExceptionWhenThereIsAViolation
     * @should returnThePostedMovieAsMovieDtoObject
     *
     * @param movieDto is the movieDto object to be posted
     * @return the posted movie as a MovieDto object
     * @throws ConstraintViolationException when there is a violation to the MovieDto constraints
     * @throws InstanceAlreadyExistsException when a similar movie exist
     */
    @Override
    public MovieDto postMovie(MovieDto movieDto) throws InstanceAlreadyExistsException {
        log.info("Entering the post movie method");
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(movieDto);
        if(violations.isEmpty()){
            if (!checkSimilarMovieExist(movieDto)) {
                log.info("There is no similar movie in the database");
                Movie movie = movieMapper.toEntity(movieDto);
                movie = movieRepository.save(movie);
                return movieMapper.toDto(movie);
            } else {
                throw new InstanceAlreadyExistsException("There is a similar movie");
            }
        }else
            throw new ConstraintViolationException("Validations were not passed",violations);
    }

    /**
     * Check if similar movie exist
     * two movies are the same if the name, release year and language are the same
     *
     * @should throwIllegalArgumentExceptionWhenMovieDtoIsNull
     * @should callGetAllMovies
     * @should returnTrueIfMoreThenZeroFoundWithSimilarNameAndReleaseYearAndLanguage
     * @should returnFalseIfNotFoundWithSimilarNameAndReleaseYearAndLanguage
     *
     * @param movieDto is the movieDto object to be used for comparison
     * @return true if similar movie was found and false if not
     * @throws IllegalArgumentException when movieDto is null
     */
    @Override
    public boolean checkSimilarMovieExist(MovieDto movieDto) {
        if(movieDto == null)
            throw new IllegalArgumentException("MovieDto cannot be null");
        int count = (int) getAllMovies().stream()
                .filter(mo -> (mo.getMovieName().equalsIgnoreCase(movieDto.getMovieName())
                && mo.getReleaseYear() == movieDto.getReleaseYear()
                && mo.getLanguage().equalsIgnoreCase(movieDto.getLanguage())))
                .count();
        if(count > 0)
            return true;
        return false;
    }

    /**
     * Deletes a movie from the database by a given Id
     *
     * @should throwIllegalArgumentExceptionWhenIdIsNull
     * @should callExistsById
     * @should callDeleteByIdIfExistsByIdReturnTrue
     * @should throwMovieNotFoundExceptionWhenIfExistsByIdReturnFalse
     *
     *
     * @param id is the movie ID to be deleted
     * @throws IllegalArgumentException when movieDto is null
     * @throws MovieNotFound when movieDto is null
     */
    @Override
    public void deleteMovie(Long id) {
        log.info("Entering the delete movie method");
        if(id == null)
            throw new IllegalArgumentException("Id cannot be null");
        if(movieRepository.existsById(id))
        {
            movieRepository.deleteById(id);
        }
        else {
            throw new MovieNotFound("Movie with the id "+id+" was not found");
        }



    }

}
