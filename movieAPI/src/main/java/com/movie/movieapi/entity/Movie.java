package com.movie.movieapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
@Data
public class Movie {

    @Id
    private Long movieid;
    private String movieName;
    private double boxOfficeRate;
    private int releaseYear;
    private String language;
    private String rated;
}
