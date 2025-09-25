package bot.movie.mate.dao;

import info.movito.themoviedbapi.model.core.popularperson.PopularPerson;

import java.util.List;

public interface CastDAO {

    List<PopularPerson> getCastByName(String name);

}
