package com.mov.movie_zone.movie;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movie")
public class MovieController {
    private final  MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Double vote_average,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double popularity)
    {
        if(title != null ){
            return movieService.getMoviesByName(title);
        } else if (language != null) {
            return movieService.getMoviesFromLanguage(language);
        } else if (vote_average != null) {
            return movieService.getMoviesByRating(vote_average);
        } else if (year != null) {
            return movieService.getMoviesByReleaseYear(year);
        } else if (popularity != null) {
            return movieService.getMoviesByPopularity(popularity);
        } else {
            return movieService.getMovies();
        }

    }

    @PostMapping//Handles Making
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie createdMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping //Handles Updates
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie){
        Movie resultMovie = movieService.updateMovie(movie);
        if(resultMovie != null){
            return new ResponseEntity<>(resultMovie, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{movieId}") //Handles Deleting
    public ResponseEntity<Integer> deleteMovie(@PathVariable Integer movieId){
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
