package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.entity.Movie;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.mapper.MovieMapper;
import com.movie.movieapi.repository.MovieRepository;
import com.movie.movieapi.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    private Validator validator;

    public MovieServiceImpl(MovieRepository movieRepository,MovieMapper movieMapper,Validator validator) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.validator = validator;
       }

    /**
     * @return
     */
    @Override
    public List<MovieDto> getAllMovies() {
        log.info("Entering the getAllMovies of MovieServiceImpl class!!");
        List<Movie> movieList = movieRepository.findAll();
        return movieMapper.toDto(movieList);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public List<MovieDto> getMoviesByName(String name) {
        List<Movie> movieList = movieRepository.findByMovieName(name);
        return movieMapper.toDto(movieList);
    }

    /**
     *
     * @param movieDto
     * @return
     */
    @Override
    public MovieDto postMovie(MovieDto movieDto) {
        log.info("Entering the post movie method");
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(movieDto);
        if(violations.isEmpty()){
            if (!checkSimilarMovieExist(movieDto)) {
                log.info("There is no similar movie in the database");
                Movie movie = movieMapper.toEntity(movieDto);
                movie = movieRepository.saveAndFlush(movie);
                return movieMapper.toDto(movie);
            } else {
                throw new MovieNotFound("There is a similar movie");
            }
        }else
            throw new ConstraintViolationException("Validations were not passed",violations);
    }

    /**
     *
     * @param movieDto
     * @return
     */
    @Override
    public boolean checkSimilarMovieExist(MovieDto movieDto) {
        int count = (int) getAllMovies().stream()
                .filter(mo -> (mo.getMovieName().equalsIgnoreCase(movieDto.getMovieName())
                && mo.getReleaseYear() == movieDto.getReleaseYear()
                && mo.getLanguage().equalsIgnoreCase(movieDto.getLanguage())))
                .count();
        if(count > 0)
            return true;
        return false;
    }

}
