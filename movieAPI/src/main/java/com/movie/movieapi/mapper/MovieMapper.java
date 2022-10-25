package com.movie.movieapi.mapper;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDto, Movie> {
}
