package bot.movie.mate.dao;

import bot.movie.mate.config.TmdbConfig;
import bot.movie.mate.literals.BotConstants;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.core.Movie;
import lombok.SneakyThrows;

import java.util.List;

public class MovieDAOImpl implements MovieDAO {

    private final TmdbApi tmdbApi = TmdbConfig.getApi();
    private final TmdbSearch tmdbSearch = tmdbApi.getSearch();
    private final TmdbMovieLists tmdbMovieLists = tmdbApi.getMovieLists();

    @SneakyThrows
    @Override
    public List<Movie> getMovieByTitle(String title) {
        return tmdbSearch.searchMovie(title, false, BotConstants.LANGUAGE, null, BotConstants.PAGE, BotConstants.REGION, null)
                         .getResults();
    }

    @SneakyThrows
    @Override
    public List<Movie> getUpcomingMovies() {
        return tmdbMovieLists.getUpcoming(BotConstants.LANGUAGE, BotConstants.PAGE, BotConstants.REGION)
                             .getResults();
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        return List.of();
    }
}
