package bot.movie.mate.converter;

import bot.movie.mate.model.CastModel;
import bot.movie.mate.model.MovieModel;
import bot.movie.mate.service.MovieService;
import info.movito.themoviedbapi.model.movies.MovieDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieDbToMovieModelConverter {

    private MovieDbToMovieModelConverter() {}

    private static final MovieService movieService = new MovieService();

    public static MovieModel convert(MovieDb movieDb) {

        CastModel director = new CastModel();
        List<CastModel> casting = new ArrayList<>();

        if (movieDb != null && movieDb.getCredits() != null) {
            director = movieService.getDirector(movieDb)
                                   .map(CrewToCastModelConverter::convert)
                                   .orElse(null);

            casting = movieService.getCasting(movieDb)
                                  .stream()
                                  .map(CastToCastModelConverter::convert)
                                  .toList();
        }

        MovieModel movieModel = new MovieModel();
        movieModel.setId(Objects.requireNonNull(movieDb).getId());
        movieModel.setTitle(movieDb.getTitle());
        movieModel.setDescription(movieDb.getOverview());
        movieModel.setPosterPath("https://image.tmdb.org/t/p/w500" + movieDb.getPosterPath());
        movieModel.setReleaseDate(movieDb.getReleaseDate());
        movieModel.setDirector(director);
        movieModel.setCast(casting);
        movieModel.setGenres(movieService.getMovieGenres(movieDb));
        return  movieModel;
    }
}
