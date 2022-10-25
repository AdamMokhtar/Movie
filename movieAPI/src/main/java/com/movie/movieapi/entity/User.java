package com.movie.movieapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Integer rolefk;


}
