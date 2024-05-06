package com.babu24.moviebookingapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveiDto {

    private Integer movieId;

    @NotBlank(message = "please provide movie title")
    private String title;

    @NotBlank(message = "please provide movie director name")
    private  String director;

    @NotBlank(message = "please provide movie studio name")
    private String studio;

    private Set<String> movieCast;
    private Integer releaseYear;

    @NotBlank(message = "please provide movie poster photo")
    private String poster;

    @NotBlank(message = "please provide poster's url")
    private String posterUrl;

}
