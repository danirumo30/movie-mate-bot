package bot.movie.mate.converter;

import bot.movie.mate.literals.BotConstants;
import bot.movie.mate.model.CastModel;
import bot.movie.mate.model.MovieModel;
import bot.movie.mate.service.MovieService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.people.PersonDb;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PopularPersonToCastModelConverter {

    private PopularPersonToCastModelConverter() {}

    private static final MovieService movieService = new MovieService();

    public static CastModel convert(PersonDb personDb) {
        List<MovieModel> movies = new ArrayList<>();

        if (personDb != null && personDb.getMovieCredits() != null) {
            movies = personDb.getMovieCredits()
                             .getCast()
                             .stream()
                             .map(movieCast -> {
                                 MovieDb movieDb = movieService.getMovieDetails(movieCast.getId(), BotConstants.LANGUAGE);
                                 return MovieDbToMovieModelConverter.convert(movieDb);
                             })
                             .toList();
        }

        return getCastModel(personDb, movies);
    }

    @NotNull
    private static CastModel getCastModel(PersonDb personDb, List<MovieModel> movies) {
        CastModel castModel = new CastModel();
        castModel.setId(Objects.requireNonNull(personDb).getId());
        castModel.setName(personDb.getName());
        castModel.setCharacter(null);
        castModel.setBirthDate(personDb.getBirthday());
        castModel.setBiography(personDb.getBiography());
        castModel.setBirthPlace(personDb.getPlaceOfBirth());
        castModel.setDeathDate(personDb.getDeathDay());
        castModel.setPhotoUrl("https://image.tmdb.org/t/p/w500" + personDb.getProfilePath());
        castModel.setMovies(movies);
        return castModel;
    }
}
