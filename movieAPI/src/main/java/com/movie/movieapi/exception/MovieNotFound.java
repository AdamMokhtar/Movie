package com.movie.movieapi.exception;

public class MovieNotFound extends RuntimeException{

    public MovieNotFound(String message){
        super(message);
    }

}
