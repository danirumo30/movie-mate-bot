package bot.movie.mate.service;

import bot.movie.mate.config.TmdbConfig;
import bot.movie.mate.converter.MovieDbToMovieModelConverter;
import bot.movie.mate.dao.MovieDAO;
import bot.movie.mate.dao.MovieDAOImpl;
import bot.movie.mate.literals.BotConstants;
import bot.movie.mate.model.MovieModel;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.Crew;
import info.movito.themoviedbapi.model.movies.MovieDb;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

public class MovieService {

    private final TmdbApi tmdbApi = TmdbConfig.getApi();
    private final MovieDAO movieDAO = new MovieDAOImpl();
    private final TmdbMovies tmdbMovies = tmdbApi.getMovies();

    public List<MovieModel> getMoviesByTitle(String title) {
        return movieDAO.getMovieByTitle(title)
                       .stream()
                       .map(movie -> {
                            MovieDb movieDb = getMovieDetails(movie.getId(), BotConstants.LANGUAGE);
                            movieDb.setCredits(getMovieCredits(movie.getId(), BotConstants.LANGUAGE));
                            return MovieDbToMovieModelConverter.convert(movieDb);
                       })
                       .toList();
    }

    public List<MovieModel> getUpcomingMovies() {
        return movieDAO.getUpcomingMovies()
                       .stream()
                       .map(movie -> {
                            MovieDb movieDb = getMovieDetails(movie.getId(), BotConstants.LANGUAGE);
                            movieDb.setCredits(getMovieCredits(movie.getId(), BotConstants.LANGUAGE));
                            return MovieDbToMovieModelConverter.convert(movieDb);
                       })
                       .toList();
    }

    @SneakyThrows
    public MovieDb getMovieDetails(int movieId, String language) {
        return tmdbMovies.getDetails(movieId, language);
    }

    @SneakyThrows
    public Credits getMovieCredits(int movieId,  String language) {
        return tmdbMovies.getCredits(movieId, language);
    }

    @SneakyThrows
    public Optional<Crew> getDirector(MovieDb movieDb) {
        return movieDb.getCredits()
                      .getCrew()
                      .stream()
                      .filter(d -> d.getJob().equals("Director"))
                      .findFirst();
    }

    public List<Cast> getCasting(MovieDb movieDb) {
        return movieDb.getCredits()
                      .getCast()
                      .stream()
                      .toList();
    }

    public List<String> getMovieGenres(MovieDb movieDb) {
        return movieDb.getGenres()
                      .stream()
                      .map(Genre::getName)
                      .toList();
    }
}
