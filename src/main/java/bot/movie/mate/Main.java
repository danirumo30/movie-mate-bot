package bot.movie.mate;

import bot.movie.mate.bot.BotListener;
import bot.movie.mate.exception.BotStartupException;
import bot.movie.mate.literals.BotConstants;
import bot.movie.mate.utils.ConfigUtils;
import bot.movie.mate.utils.TmdbEmbedManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            TmdbEmbedManager tmdbEmbedManager = new TmdbEmbedManager();
            JDA jda = JDABuilder.createDefault(
                                    ConfigUtils.get("discord.token"),
                                    GatewayIntent.GUILD_MESSAGES,
                                    GatewayIntent.MESSAGE_CONTENT,
                                    GatewayIntent.GUILD_MESSAGE_REACTIONS
                                )
                                .addEventListeners(new BotListener(tmdbEmbedManager), tmdbEmbedManager)
                                .build();

            jda.awaitReady();
            LOGGER.info(BotConstants.PURPLE + "Bot is ready!" + BotConstants.RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Bot startup was interrupted", e);
        } catch (Exception _) {
            throw new BotStartupException("Failed to start the bot");
        }
    }
}
