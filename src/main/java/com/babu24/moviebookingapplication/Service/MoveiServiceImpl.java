package com.babu24.moviebookingapplication.Service;

import com.babu24.moviebookingapplication.Entity.Movei;
import com.babu24.moviebookingapplication.Repositry.MovieRepositry;
import com.babu24.moviebookingapplication.dto.MoveiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
       String uploadFileName= fileService.uploadFile(path,file);

        // 2. set the value of field 'poster' as filename
        moveiDto.setPoster(uploadFileName);

        // 3. map dto Movie object
        Movei movei=new Movei(
              moveiDto.getMovieId(),
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
        return null;
    }

    @Override
    public List<MoveiDto> getAllMovies() {
        return null;
    }
}
