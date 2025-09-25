package bot.movie.mate.dao;

import info.movito.themoviedbapi.model.core.Movie;

import java.util.List;

public interface MovieDAO {

    List<Movie> getMovieByTitle(String title);
    List<Movie> getUpcomingMovies();
    List<Movie> getMoviesByGenre(String genre);

}
