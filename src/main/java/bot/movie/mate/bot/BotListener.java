package bot.movie.mate.bot;

import bot.movie.mate.command.CastNameCommand;
import bot.movie.mate.command.Command;
import bot.movie.mate.command.MovieTitleCommand;
import bot.movie.mate.command.MovieUpcomingCommand;
import bot.movie.mate.utils.TmdbEmbedManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BotListener extends ListenerAdapter {

    private final Map<String, Command> commandMap = new HashMap<>();

    public BotListener(TmdbEmbedManager tmdbEmbedManager) {
        commandMap.put("estrenos", new MovieUpcomingCommand(tmdbEmbedManager));
        commandMap.put("pelicula", new MovieTitleCommand(tmdbEmbedManager));
        commandMap.put("actor", new CastNameCommand(tmdbEmbedManager));
        commandMap.put("mate-clear", new bot.movie.mate.command.ClearCommand());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String content = event.getMessage().getContentRaw();
        if (!content.startsWith("!")) {
            return;
        }

        String[] split = content.substring(1).split("\\s+");
        String cmdName = split[0].toLowerCase();
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        Command command = commandMap.get(cmdName);
        if (command != null) {
            command.execute(event, args);
        }
    }
}
