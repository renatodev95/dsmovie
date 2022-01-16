package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dtos.MovieDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Pageable pageable) {
        Page<Movie> movieList = repository.findAll(pageable);
        return movieList.map(MovieDTO::new);
    }

    public MovieDTO findById(Long id) {
        return new MovieDTO(repository.findById(id).get());
    }
}
