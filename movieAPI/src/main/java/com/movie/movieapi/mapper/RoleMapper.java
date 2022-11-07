package com.movie.movieapi.mapper;

import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
}
