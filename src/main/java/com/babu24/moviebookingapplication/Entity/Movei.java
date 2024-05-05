package com.babu24.moviebookingapplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movei {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false)
    @NotNull(message = "please provide movie title")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "please provide movie director name")
    private  String director;

    @Column(nullable = false)
    @NotNull(message = "please provide movie studio name")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "please provide movie release year name")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotNull(message = "please provide movie poster photo")
    private String poster;

}
