package com.babu24.moviebookingapplication.Exception;

public class MovieNotFoundExpection extends RuntimeException{
    public MovieNotFoundExpection(String message){
        super(message);
    }
}
