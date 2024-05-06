package com.babu24.moviebookingapplication.Controller;

import com.babu24.moviebookingapplication.Service.MovieService;
import com.babu24.moviebookingapplication.dto.MoveiDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/movie")
public class MoveiController {

    private final MovieService movieService;

    public MoveiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MoveiDto> addMovieHandler(
            @RequestPart MultipartFile file,@RequestPart String movieData) throws IOException {

        MoveiDto dto=convertToMovieDto(movieData);
        return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    // this method convert string to json
    private MoveiDto convertToMovieDto(String movieStoObj) throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
       return objectMapper.readValue(movieStoObj,MoveiDto.class);
    }
}
