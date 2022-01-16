package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dtos.MovieDTO;
import com.devsuperior.dsmovie.dtos.ScoreDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.entities.Score;
import com.devsuperior.dsmovie.entities.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public MovieDTO saveScore(ScoreDTO dto) {
        //Lógica:
        //Informar email, id do filme e valor da avaliação (1 a 5).
        //Recuperar usuário do banco de dados pelo email. Se o usuário não existir, insira no banco.
        //Salvar a avaliação do usuário para o dado filme.
        // Recalcular a avaliação média do filme e salvar no banco de dados.
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            user = userRepository.saveAndFlush(new User(null, dto.getEmail()));
        }

        Movie movie = movieRepository.findById(dto.getMovieId()).get();

        Score score = new Score();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(dto.getScore());
        score = scoreRepository.saveAndFlush(score);

        double sum = 0.0;
        for (Score s : movie.getScores()) {
            sum += s.getValue();
        }
        double avg = sum / movie.getScores().size();

        movie.setScore(avg);
        movie.setCount(movie.getScores().size());

        movieRepository.save(movie);

        return new MovieDTO(movie);
    }

}
