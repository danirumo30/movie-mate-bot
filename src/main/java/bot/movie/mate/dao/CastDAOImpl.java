package bot.movie.mate.dao;

import bot.movie.mate.config.TmdbConfig;
import bot.movie.mate.literals.BotConstants;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.core.popularperson.PopularPerson;
import lombok.SneakyThrows;

import java.util.List;

public class CastDAOImpl implements CastDAO {

    private final TmdbApi tmdbApi = TmdbConfig.getApi();
    private final TmdbSearch tmdbSearch = tmdbApi.getSearch();

    @SneakyThrows
    @Override
    public List<PopularPerson> getCastByName(String name) {
        return tmdbSearch.searchPerson(name, false, BotConstants.LANGUAGE, BotConstants.PAGE).getResults();
    }
}
