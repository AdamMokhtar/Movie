package com.movie.movieapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_rating")
@Data
public class UserRating {

    @Id
    private Integer id;
    private Long movieId;
    private Integer userId;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "movieId", insertable = false, updatable = false)
    private Movie movie ;

    @ManyToOne
    @JoinColumn(name = "userId" , insertable = false, updatable = false)
    private User user ;
}
