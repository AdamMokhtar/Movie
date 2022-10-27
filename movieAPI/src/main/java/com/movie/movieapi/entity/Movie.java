package com.movie.movieapi.entity;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "movie")
@Data
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", unique=true)
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
