package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.entity.Movie;
import com.movie.movieapi.mapper.MovieMapper;
import com.movie.movieapi.repository.MovieRepository;
import com.movie.movieapi.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository,MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
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
        Movie movie = movieMapper.toEntity(movieDto);
        movie = movieRepository.saveAndFlush(movie);
        return movieMapper.toDto(movie);
    }

}
