package bot.movie.mate.utils;

import bot.movie.mate.model.MovieModel;
import bot.movie.mate.model.CastModel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class TmdbEmbedManager extends ListenerAdapter {

    private List<MovieModel> movies;
    private List<CastModel> castListForReaction;
    private int currentMovieIndex = 0;
    private int currentCastIndex = 0;
    private boolean useProfileEmbed = false;

    private Message movieMessage;
    private Message castMessage;
    private Message lastUserMessage;

    public void sendMovies(TextChannel channel, List<MovieModel> movies, Message userMessage) {
        reset();
        useProfileEmbed = false;

        if (clearOldMessages(userMessage, movies.isEmpty())) {
            return;
        }

        this.movies = movies;
        this.currentMovieIndex = 0;
        this.currentCastIndex = 0;

        MovieModel movie = movies.get(currentMovieIndex);

        channel.sendMessageEmbeds(EmbedUtils.createMovieEmbed(movie)).queue(msg -> {
            this.movieMessage = msg;
            msg.addReaction(Emoji.fromUnicode("⬅️")).queue();
            msg.addReaction(Emoji.fromUnicode("➡️")).queue();
        });

        if (movie.getCast() != null && !movie.getCast().isEmpty()) {
            channel.sendMessageEmbeds(EmbedUtils.createCastEmbed(movie.getCast().get(currentCastIndex)))
                    .queue(msg -> {
                        this.castMessage = msg;
                        msg.addReaction(Emoji.fromUnicode("⬅️")).queue();
                        msg.addReaction(Emoji.fromUnicode("➡️")).queue();
                    });
        }
    }

    public void sendCast(TextChannel channel, List<CastModel> castList, Message userMessage) {
        reset();
        useProfileEmbed = true;

        if (clearOldMessages(userMessage, castList.isEmpty())) {
            return;
        }

        this.castListForReaction = castList;
        this.currentCastIndex = 0;
        CastModel cast = castList.get(currentCastIndex);

        channel.sendMessageEmbeds(EmbedUtils.createCastProfileEmbed(cast)).queue(msg -> {
            this.castMessage = msg;
            msg.addReaction(Emoji.fromUnicode("⬅️")).queue();
            msg.addReaction(Emoji.fromUnicode("➡️")).queue();
        });

        if (cast.getMovies() != null && !cast.getMovies().isEmpty()) {
            this.movies = cast.getMovies();
            this.currentMovieIndex = 0;

            MovieModel movie = movies.get(currentMovieIndex);
            channel.sendMessageEmbeds(EmbedUtils.createMovieEmbed(movie)).queue(msg -> {
                this.movieMessage = msg;
                msg.addReaction(Emoji.fromUnicode("⬅️")).queue();
                msg.addReaction(Emoji.fromUnicode("➡️")).queue();
            });
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }

        String emoji = event.getReaction().getEmoji().getName();

        if (movieMessage != null && event.getMessageId().equals(movieMessage.getId())) {
            if ("➡️".equals(emoji)) {
                currentMovieIndex = (currentMovieIndex + 1) % movies.size();
                currentCastIndex = 0;
                updateMovieAndCast();
            } else if ("⬅️".equals(emoji)) {
                currentMovieIndex = (currentMovieIndex - 1 + movies.size()) % movies.size();
                currentCastIndex = 0;
                updateMovieAndCast();
            }
            event.getReaction().removeReaction(event.getUser()).queue();
            return;
        }

        if (castMessage != null && event.getMessageId().equals(castMessage.getId())) {
            List<CastModel> cast;
            if (castListForReaction != null && !castListForReaction.isEmpty()) {
                cast = castListForReaction;
            } else {
                MovieModel movie = movies.get(currentMovieIndex);
                cast = movie.getCast();
            }

            if (cast == null || cast.isEmpty()) {
                return;
            }

            if ("➡️".equals(emoji)) {
                currentCastIndex = (currentCastIndex + 1) % cast.size();
                updateCastMessage();
            } else if ("⬅️".equals(emoji)) {
                currentCastIndex = (currentCastIndex - 1 + cast.size()) % cast.size();
                updateCastMessage();
            }

            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }

    private void updateMovieAndCast() {
        MovieModel movie = movies.get(currentMovieIndex);
        movieMessage.editMessageEmbeds(EmbedUtils.createMovieEmbed(movie)).queue();

        if (movie.getCast() != null && !movie.getCast().isEmpty()) {
            castMessage.editMessageEmbeds(EmbedUtils.createCastEmbed(movie.getCast().get(currentCastIndex))).queue();
        }
    }

    private void updateCastMessage() {
        CastModel cast;
        if (castListForReaction != null && !castListForReaction.isEmpty()) {
            cast = castListForReaction.get(currentCastIndex);
        } else {
            MovieModel movie = movies.get(currentMovieIndex);
            cast = movie.getCast().get(currentCastIndex);
        }

        if (useProfileEmbed) {
            castMessage.editMessageEmbeds(EmbedUtils.createCastProfileEmbed(cast)).queue();
        } else {
            castMessage.editMessageEmbeds(EmbedUtils.createCastEmbed(cast)).queue();
        }
    }

    private boolean clearOldMessages(Message userMessage, boolean empty) {
        if (empty) {
            return true;
        }

        if (movieMessage != null) {
            movieMessage.delete().queue(_ -> {});
            movieMessage = null;
        }

        if (castMessage != null) {
            castMessage.delete().queue(_ -> {});
            castMessage = null;
        }

        if (lastUserMessage != null && lastUserMessage.getAuthor().equals(userMessage.getAuthor())) {
            lastUserMessage.delete().queue(_ -> {});
        }

        lastUserMessage = userMessage;
        return false;
    }

    public void reset() {
        movies = null;
        castListForReaction = null;
        currentMovieIndex = 0;
        currentCastIndex = 0;
        movieMessage = null;
        castMessage = null;
    }
}
