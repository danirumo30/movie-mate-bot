package bot.movie.mate.config;

import bot.movie.mate.utils.ConfigUtils;
import info.movito.themoviedbapi.TmdbApi;

public class TmdbConfig {

    private TmdbConfig () {}

    private static final String API_KEY = ConfigUtils.get("tmdb.apiKey");
    private static final TmdbApi tmdbApi = new TmdbApi(API_KEY);

    public static TmdbApi getApi() {
        return tmdbApi;
    }
}
