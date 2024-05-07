package com.babu24.moviebookingapplication.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GolbalExceptionHandler {

    @ExceptionHandler(MovieNotFoundExpection.class)
    public ProblemDetail handelMovieNotFoundException(MovieNotFoundExpection ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND
        , ex.getMessage());
    }

    @ExceptionHandler(FileExitException.class)
    public ProblemDetail handelFileExitException(FileExitException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST
                , ex.getMessage());
    }

    @ExceptionHandler(EmptyFileException.class)
    public ProblemDetail handelEmptyFileException(EmptyFileException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST
                , ex.getMessage());
    }

}
