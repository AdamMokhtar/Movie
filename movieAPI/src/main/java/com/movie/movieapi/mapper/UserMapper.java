package com.movie.movieapi.mapper;


import com.movie.movieapi.dto.UserDto;
import com.movie.movieapi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User>{
}
