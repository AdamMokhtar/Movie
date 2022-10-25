package com.movie.movieapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
@Data
public class Role {

    @Id
    private Integer roleId;
    private String roleName;
}
