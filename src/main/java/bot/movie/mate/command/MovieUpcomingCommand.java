package bot.movie.mate.command;

import bot.movie.mate.model.MovieModel;
import bot.movie.mate.service.MovieService;
import bot.movie.mate.utils.TmdbEmbedManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class MovieUpcomingCommand implements Command {

    private final MovieService movieService = new MovieService();
    private final TmdbEmbedManager tmdbEmbedManager;

    public MovieUpcomingCommand(TmdbEmbedManager tmdbEmbedManager) {
        this.tmdbEmbedManager = tmdbEmbedManager;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        List<MovieModel> movies = movieService.getUpcomingMovies();
        if (!movies.isEmpty()) {
            tmdbEmbedManager.sendMovies((TextChannel) event.getChannel(), movies, event.getMessage());
        } else {
            event.getChannel().sendMessage("No hay estrenos próximos disponibles").queue();
        }
    }
}
