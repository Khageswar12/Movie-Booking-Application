package com.babu24.moviebookingapplication.Service;

import com.babu24.moviebookingapplication.Entity.Movei;
import com.babu24.moviebookingapplication.Exception.FileExitException;
import com.babu24.moviebookingapplication.Exception.MovieNotFoundExpection;
import com.babu24.moviebookingapplication.Repositry.MovieRepositry;
import com.babu24.moviebookingapplication.dto.MoveiDto;
import com.babu24.moviebookingapplication.dto.MoviePageResponce;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoveiServiceImpl implements MovieService{

    private final MovieRepositry movieRepositry;
    private final FileService fileService;

    @Value("${project.poster}")
     private String path;
    @Value("${base.url}")
    private String baseUrl;

    public MoveiServiceImpl(MovieRepositry movieRepositry, FileService fileService) {
        this.movieRepositry = movieRepositry;
        this.fileService = fileService;
    }

    @Override
    public MoveiDto addMovie(MoveiDto moveiDto, MultipartFile file) throws IOException {
        // 1. upload the file
        if(Files.exists(Paths.get(path + File.separator +file.getOriginalFilename()))){
            throw new FileExitException("file already exits ! please enter another file name");
        }
       String uploadFileName= fileService.uploadFile(path,file);

        // 2. set the value of field 'poster' as filename
        moveiDto.setPoster(uploadFileName);

        // 3. map dto Movie object
        Movei movei=new Movei(
              null,
              moveiDto.getTitle(),
                moveiDto.getDirector(),
                moveiDto.getStudio(),
                moveiDto.getMovieCast(),
                moveiDto.getReleaseYear(),
                moveiDto.getPoster()

        );

        // 4. save the movie object -> saved movie object
        Movei savedMovie= movieRepositry.save(movei);

        // 5. generate the posterUrl
          String posterUrl= baseUrl + "/file/" + uploadFileName;

        // 6. map movie object to dto object and return it
        MoveiDto response= new MoveiDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public MoveiDto getMovie(Integer movieId) {
        // 1. check the data in db and if exits, fetch the data of given id
        Movei movei= movieRepositry.findById(movieId).orElseThrow(()->
                new MovieNotFoundExpection("movie not found with id"+movieId));

        // 2. generate posterUrl
        String posterUrl= baseUrl + "/file/" + movei.getPoster();

        // 3. map to movieDto object and return it
        MoveiDto response= new MoveiDto(
                movei.getMovieId(),
                movei.getTitle(),
                movei.getDirector(),
                movei.getStudio(),
                movei.getMovieCast(),
                movei.getReleaseYear(),
                movei.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public List<MoveiDto> getAllMovies() {
        // 1. fetch all data from DB
        List<Movei> moveis=movieRepositry.findAll();

          List<MoveiDto> moveiDtos=new ArrayList<>();
        // 2. iterate through the list, generate posterUrl for each movie obj.
          for(Movei movei:moveis){
              String posterUrl= baseUrl + "/file/" + movei.getPoster();
              MoveiDto response= new MoveiDto(
                      movei.getMovieId(),
                      movei.getTitle(),
                      movei.getDirector(),
                      movei.getStudio(),
                      movei.getMovieCast(),
                      movei.getReleaseYear(),
                      movei.getPoster(),
                      posterUrl
              );
              moveiDtos.add(response);
          }
        // 3. and map to movieDto obj
        return moveiDtos;
    }

    @Override
    public MoveiDto updateMovie(Integer movieId, MoveiDto moveiDto, MultipartFile file) throws IOException{

     // 1. check if movie object with given id
        Movei mv= movieRepositry.findById(movieId).orElseThrow(()->
                new MovieNotFoundExpection("movie not found"));
     // 2. if the file is null,do nothing &   if file is not null

        String fileName=mv.getPoster();
        if(file != null){
            Files.deleteIfExists(Paths.get(path +File.separator +fileName));
            fileName=fileService.uploadFile(path,file);
        }
     // then delete exiting file associated with the record
     //and upload the new file

     // 3. set movieDto poster value, according to step 2
        moveiDto.setPoster(fileName);
     //4. map it to movie object
        Movei movei = new Movei(
                mv.getMovieId(),
                moveiDto.getTitle(),
                moveiDto.getDirector(),
                moveiDto.getStudio(),
                moveiDto.getMovieCast(),
                moveiDto.getReleaseYear(),
                moveiDto.getPoster()
        );
        // 5. save the movie object -> return saved movie object
          Movei updateMovie= movieRepositry.save(movei);
          // 6. generate posterUrl for it
        String posterUrl=baseUrl + "/file/" + fileName;
        // 7. map to MovieDto and return it
        MoveiDto response= new MoveiDto(
                movei.getMovieId(),
                movei.getTitle(),
                movei.getDirector(),
                movei.getStudio(),
                movei.getMovieCast(),
                movei.getReleaseYear(),
                movei.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {

        // 1. check if movie object exit in db
        Movei mv= movieRepositry.findById(movieId).orElseThrow(()->
                new MovieNotFoundExpection("movie not found"));
        Integer id=mv.getMovieId();

        // 2. delete the file associated with this object
           Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));

        // 3. delete the movie object
        movieRepositry.delete(mv);
        return "Movie deleted with id = "+id;
    }

    @Override
    public MoviePageResponce getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movei> moveiPages = movieRepositry.findAll(pageable);
        List<Movei> moveis = moveiPages.getContent();
        

        List<MoveiDto> moveiDtos=new ArrayList<>();
        // 2. iterate through the list, generate posterUrl for each movie obj.
        for(Movei movei:moveis){
            String posterUrl= baseUrl + "/file/" + movei.getPoster();
            MoveiDto response= new MoveiDto(
                    movei.getMovieId(),
                    movei.getTitle(),
                    movei.getDirector(),
                    movei.getStudio(),
                    movei.getMovieCast(),
                    movei.getReleaseYear(),
                    movei.getPoster(),
                    posterUrl
            );
            moveiDtos.add(response);
        }

      return new MoviePageResponce(
              moveiDtos,pageNumber,pageSize,
              moveiPages.getTotalElements(),
              moveiPages.getTotalPages(),
              moveiPages.isLast()
      );
    }


    @Override
        public MoviePageResponce getAllMoviesWithPaginationAndSorting (Integer pageNumber,
                Integer pageSize, String sortBy, String dir){

        Sort sort=dir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Movei> moveiPages = movieRepositry.findAll(pageable);
        List<Movei> moveis = moveiPages.getContent();


        List<MoveiDto> moveiDtos=new ArrayList<>();
        // 2. iterate through the list, generate posterUrl for each movie obj.
        for(Movei movei:moveis){
            String posterUrl= baseUrl + "/file/" + movei.getPoster();
            MoveiDto response= new MoveiDto(
                    movei.getMovieId(),
                    movei.getTitle(),
                    movei.getDirector(),
                    movei.getStudio(),
                    movei.getMovieCast(),
                    movei.getReleaseYear(),
                    movei.getPoster(),
                    posterUrl
            );
            moveiDtos.add(response);
        }

        return new MoviePageResponce(
                moveiDtos,pageNumber,pageSize,
                moveiPages.getTotalElements(),
                moveiPages.getTotalPages(),
                moveiPages.isLast()
        );
    }

    
}
