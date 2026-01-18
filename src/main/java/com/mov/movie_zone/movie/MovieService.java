package com.mov.movie_zone.movie;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesFromLanguage(String language) {
        return movieRepository.findAll().stream().filter(movie -> language.equalsIgnoreCase(
                movie.getLanguage())).collect(Collectors.toList());
    }

    public List<Movie> getMoviesByName(String searchText){
        return movieRepository.findAll().stream().filter(movie -> movie.getTitle().toLowerCase()
                .contains(searchText.toLowerCase())).collect(Collectors.toList());
    }

    public List<Movie> getMoviesByRating(Double minRating){
        return movieRepository.findAll().stream().filter(movie -> movie.getVote_average() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByReleaseYear(Integer year){
        return movieRepository.findAll().stream().filter(movie ->
                movie.getRelease_date().startsWith(String.valueOf(year)))
                .sorted(Comparator.comparing(Movie::getRelease_date))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByPopularity(Double minPopularity){
        return movieRepository.findAll().stream().filter(movie -> movie.getPopularity() >= minPopularity)
                .collect(Collectors.toList());
    }

    public Movie addMovie(Movie movie){
        movieRepository.save(movie);
        return movie;
    }

    public Movie updateMovie(Movie updatedMovie){
        Optional<Movie> existingMovie = movieRepository.findById(updatedMovie.getId());
        if(existingMovie.isPresent()){
            Movie movieToUpdate = existingMovie.get();
            movieToUpdate.setId(updatedMovie.getId());
            movieToUpdate.setLanguage(updatedMovie.getLanguage());
            movieToUpdate.setOverview(updatedMovie.getOverview());
            movieToUpdate.setPopularity(updatedMovie.getPopularity());
            movieToUpdate.setTitle(updatedMovie.getTitle());
            movieToUpdate.setRelease_date(updatedMovie.getRelease_date());
            movieToUpdate.setVote_average(updatedMovie.getVote_average());

            movieRepository.save(movieToUpdate);
            return movieToUpdate;
        }
        return null;
    }

    @Transactional
    public void deleteMovie(Integer id){
        movieRepository.deleteById(id);
    }

}
