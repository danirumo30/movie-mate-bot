package bot.movie.mate.service;

import bot.movie.mate.config.TmdbConfig;
import bot.movie.mate.converter.PopularPersonToCastModelConverter;
import bot.movie.mate.dao.CastDAO;
import bot.movie.mate.dao.CastDAOImpl;
import bot.movie.mate.literals.BotConstants;
import bot.movie.mate.model.CastModel;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.people.PersonDb;
import info.movito.themoviedbapi.model.people.credits.MovieCredits;
import lombok.SneakyThrows;

import java.util.List;

public class CastService {

    private final CastDAO castDAO = new CastDAOImpl();
    private final TmdbApi tmdbApi = TmdbConfig.getApi();
    private final TmdbPeople tmdbPeople = tmdbApi.getPeople();

    public List<CastModel> getCastByName(String name) {
        return castDAO.getCastByName(name)
                      .stream()
                      .map(popularPerson -> {
                          PersonDb personDb = getPersonDetails(popularPerson.getId(), BotConstants.LANGUAGE);
                          personDb.setMovieCredits(getPersonCredits(popularPerson.getId(), BotConstants.LANGUAGE));
                          return PopularPersonToCastModelConverter.convert(personDb);
                      })
                      .toList();
    }

    @SneakyThrows
    public PersonDb getPersonDetails(int personId, String language) {
        return tmdbPeople.getDetails(personId, language);
    }

    @SneakyThrows
    public MovieCredits getPersonCredits(int personId, String language) {
        return tmdbPeople.getMovieCredits(personId, language);
    }
}
