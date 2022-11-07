package com.movie.movieapi.repository;

import com.movie.movieapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleNameIgnoreCase(String name);
}
