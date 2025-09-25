package bot.movie.mate.converter;

import bot.movie.mate.model.CastModel;
import info.movito.themoviedbapi.model.movies.Crew;

public class CrewToCastModelConverter {

    private CrewToCastModelConverter() {}

    public static CastModel convert(Crew crew) {
        if (crew != null) {
            CastModel castModel = new CastModel();
            castModel.setId(crew.getId());
            castModel.setName(crew.getName());
            castModel.setCharacter(crew.getName());
            castModel.setPhotoUrl("https://image.tmdb.org/t/p/w500" + crew.getProfilePath());
            return castModel;
        }
        return null;
    }
}
