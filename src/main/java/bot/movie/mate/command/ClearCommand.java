package bot.movie.mate.command;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ClearCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (!(event.getChannel() instanceof TextChannel channel)) {
            return;
        }

        channel.getHistory().retrievePast(100).queue(messages -> {
            if (messages.isEmpty()) {
                return;
            }

            channel.deleteMessages(messages).queue(
                    _ -> channel.sendMessage("Mensajes borrados correctamente.").queue(),
                    _ -> channel.sendMessage("No se pudieron borrar los mensajes.").queue()
            );
        });
    }
}
