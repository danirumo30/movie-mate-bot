package bot.movie.mate.converter;

import bot.movie.mate.model.CastModel;
import info.movito.themoviedbapi.model.movies.Cast;

public class CastToCastModelConverter {

    private CastToCastModelConverter() {}

    public static CastModel convert(Cast cast) {

        if (cast != null) {
            CastModel castModel = new CastModel();
            castModel.setId(cast.getId());
            castModel.setName(cast.getName());
            castModel.setCharacter(cast.getName());
            castModel.setPhotoUrl("https://image.tmdb.org/t/p/w500" + cast.getProfilePath());
            return castModel;
        }
        return null;
    }
}
