package bot.movie.mate.command;

import bot.movie.mate.model.CastModel;
import bot.movie.mate.service.CastService;
import bot.movie.mate.utils.TmdbEmbedManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CastNameCommand implements Command {
    private final CastService castService = new CastService();
    private final TmdbEmbedManager tmdbEmbedManager;

    public CastNameCommand(TmdbEmbedManager tmdbEmbedManager) {
        this.tmdbEmbedManager = tmdbEmbedManager;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        String name = String.join(" ", args);

        List<CastModel> cast = castService.getCastByName(name);
        if (!cast.isEmpty()) {
            tmdbEmbedManager.sendCast((TextChannel) event.getChannel(), cast, event.getMessage());
        } else {
            event.getChannel().sendMessage("No hay actores/actrices disponibles").queue();
        }
    }
}
