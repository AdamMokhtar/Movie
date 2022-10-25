package com.movie.movieapi.entity;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movie")
@Data
public class Movie implements Serializable {

    @Id
    @Column(name = "movie_id")
    private Long movieid;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "box_office_rate")
    private double boxOfficeRate;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "language")
    private String language;

    @Column(name = "rated")
    private String rated;
}
