package com.babu24.moviebookingapplication.Service;

import com.babu24.moviebookingapplication.dto.MoveiDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MoveiDto addMovie(MoveiDto moveiDto, MultipartFile file) throws IOException;

    MoveiDto getMovie(Integer movieId);

    List<MoveiDto> getAllMovies();
}
