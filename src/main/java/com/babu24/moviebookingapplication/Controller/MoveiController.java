package com.babu24.moviebookingapplication.Controller;

import com.babu24.moviebookingapplication.Exception.EmptyFileException;
import com.babu24.moviebookingapplication.Service.MovieService;
import com.babu24.moviebookingapplication.Utils.AppConstances;
import com.babu24.moviebookingapplication.dto.MoveiDto;
import com.babu24.moviebookingapplication.dto.MoviePageResponce;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MoveiController {

    private final MovieService movieService;

    public MoveiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MoveiDto> addMovieHandler(
            @RequestPart MultipartFile file,@RequestPart String movieData) throws IOException, EmptyFileException {

        if(file.isEmpty()){
            throw new EmptyFileException("file is empty " +
                    " please send another file");
        }
        MoveiDto dto=convertToMovieDto(movieData);
        return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MoveiDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService
                .getMovie(movieId)) ;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MoveiDto>> getMoviesHandler(){
        return ResponseEntity.ok(movieService.getAllMovies()) ;
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<MoveiDto>updateMovieHandler(@PathVariable Integer movieId,
                                                      @RequestPart MultipartFile file,
                                                      @RequestPart String movieDtoObj) throws IOException {
       if(file.isEmpty()) file=null;
       MoveiDto dto=convertToMovieDto(movieDtoObj);
       return ResponseEntity.ok(movieService.updateMovie(movieId,dto,file));
    }
    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String>deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviespage")
    public ResponseEntity<MoviePageResponce> getMoviesWithPagination(
            @RequestParam(defaultValue = AppConstances.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(defaultValue = AppConstances.PAGE_SIZE,required = false)Integer pageSize
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));

    }

    @GetMapping("/allMoviespageSort")
    public ResponseEntity<MoviePageResponce> getMoviesWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstances.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(defaultValue = AppConstances.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(defaultValue = AppConstances.SORT_BY,required = false)String sortBy,
            @RequestParam(defaultValue = AppConstances.SORT_DIR,required = false)String dir
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));

    }











    // this method convert string to json
    private MoveiDto convertToMovieDto(String movieStoObj) throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
       return objectMapper.readValue(movieStoObj,MoveiDto.class);
    }


}
