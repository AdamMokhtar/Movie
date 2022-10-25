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
        log.info("snehaaaaaaa");
        return movieMapper.toDto(movieList);
    }
}
