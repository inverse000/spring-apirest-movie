package com.inverse.restdatajpapropio.controller;

import com.inverse.restdatajpapropio.entities.Movie;
import com.inverse.restdatajpapropio.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository){
        this.movieRepository =movieRepository;
    }

    //CRUD
    @GetMapping("/api/movies")
    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    @GetMapping("/api/movies/{id}")
    public ResponseEntity<Movie> findOneById(@PathVariable Long id){
        Optional<Movie> movieOp = movieRepository.findById(id);
        if(movieOp.isPresent()){
            return ResponseEntity.ok(movieOp.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/api/movies") //ver el primer slash
    public ResponseEntity<Movie>create (@RequestBody Movie movie){
        if(movie.getId() != null){
            System.out.println("Trying to create a movie with id (already exist go to modify)");
            return ResponseEntity.badRequest().build();
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/movies")
    public ResponseEntity<Movie> update(@RequestBody Movie movie){
        if(movie.getId() == null){
            System.out.println("Trying to update movie without id (don't existe yet go to create)");
            return ResponseEntity.badRequest().build();
        }
        if(!movieRepository.existsById(movie.getId())){
            System.out.println("Trying to update an inexistent movie");
            return ResponseEntity.notFound().build();
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity<Movie> delete (@PathVariable Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            System.out.println("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/api/movies")
    public ResponseEntity<Movie> deleteAll(){
        System.out.println("Deleting everything...");
        movieRepository.deleteAll();
        return ResponseEntity.notFound().build();
    }
}
