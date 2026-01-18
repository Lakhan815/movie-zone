package com.mov.movie_zone.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    void deleteById(Integer id);

    Optional<Movie> findById(Integer id);
}


