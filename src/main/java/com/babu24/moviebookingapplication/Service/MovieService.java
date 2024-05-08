package com.babu24.moviebookingapplication.Service;

import com.babu24.moviebookingapplication.dto.MoveiDto;
import com.babu24.moviebookingapplication.dto.MoviePageResponce;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MoveiDto addMovie(MoveiDto moveiDto, MultipartFile file) throws IOException;

    MoveiDto getMovie(Integer movieId);

    List<MoveiDto> getAllMovies();

    MoveiDto updateMovie(Integer movieId,MoveiDto moveiDto,MultipartFile file) throws IOException;

    String deleteMovie(Integer movieId) throws IOException;

    MoviePageResponce getAllMoviesWithPagination(Integer pageNumber,Integer pageSize);

    MoviePageResponce getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy,String dir);
}
