package bot.movie.mate.utils;

import bot.movie.mate.literals.BotConstants;
import bot.movie.mate.model.CastModel;
import bot.movie.mate.model.MovieModel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedUtils {

    private EmbedUtils() {}

    public static MessageEmbed createMovieEmbed(MovieModel movie) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(movie.getTitle() != null ? movie.getTitle() : "Título desconocido");
        eb.setDescription(movie.getDescription() != null ? movie.getDescription() : "Sin descripción disponible");
        eb.setColor(new Color(128, 0, 128));
        eb.setFooter("MovieMate Bot");

        String releaseDate = (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
                ? movie.getReleaseDate() : BotConstants.UNKNOWN;
        eb.addField("Fecha de lanzamiento", releaseDate, false);

        if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
            eb.setImage(movie.getPosterPath());
        }

        String directorName = (movie.getDirector() != null && movie.getDirector().getName() != null)
                ? movie.getDirector().getName() : "Desconocido";
        eb.addField("Director", directorName, true);

        String genres = (movie.getGenres() != null && !movie.getGenres().isEmpty())
                ? String.join(", ", movie.getGenres()) : "Desconocidos";
        eb.addField("Géneros", genres, true);

        eb.setTimestamp(java.time.Instant.now());
        return eb.build();
    }

    public static MessageEmbed createCastEmbed(CastModel cast) {
        EmbedBuilder eb = new EmbedBuilder();

        String name = (cast.getName() != null) ? cast.getName() : "Nombre " + BotConstants.UNKNOWN;
        eb.addField("Nombre", name, true);

        String character = (cast.getCharacter() != null && !cast.getCharacter().isEmpty())
                ? cast.getCharacter() : BotConstants.UNKNOWN;
        eb.addField("Personaje", character, true);

        if (cast.getPhotoUrl() != null && !cast.getPhotoUrl().isEmpty()) {
            eb.setImage(cast.getPhotoUrl());
        }

        eb.setColor(Color.MAGENTA);
        return eb.build();
    }

    public static MessageEmbed createCastProfileEmbed(CastModel cast) {
        EmbedBuilder eb = new EmbedBuilder();

        String name = (cast.getName() != null) ? cast.getName() : "Nombre desconocido";
        eb.setTitle(name);
        eb.setDescription(cast.getBiography() != null ? cast.getBiography() : "Sin descripción disponible");

        String birthDate = (cast.getBirthDate() != null && !cast.getBirthDate().isEmpty())
                ? cast.getBirthDate() : BotConstants.UNKNOWN;
        eb.addField("Fecha de nacimiento", birthDate, true);

        if (cast.getPhotoUrl() != null && !cast.getPhotoUrl().isEmpty()) {
            eb.setImage(cast.getPhotoUrl());
        }

        String deathDate = (cast.getDeathDate() != null && !cast.getDeathDate().isEmpty())
                ? cast.getDeathDate() : BotConstants.UNKNOWN;
        eb.addField("Fecha de muerte", deathDate, false);

        eb.setColor(Color.MAGENTA);
        eb.setFooter("MovieMate Bot");
        eb.setTimestamp(java.time.Instant.now());

        return eb.build();
    }
}
