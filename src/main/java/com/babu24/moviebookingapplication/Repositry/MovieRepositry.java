package com.babu24.moviebookingapplication.Repositry;

import com.babu24.moviebookingapplication.Entity.Movei;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepositry extends JpaRepository<Movei,Integer> {

}
