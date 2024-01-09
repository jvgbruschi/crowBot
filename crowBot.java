import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class CrowBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault("TOKEN_DO_SEU_BOT");
        builder.addEventListeners(new CrowBot());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        TextChannel channel = event.getTextChannel();

        if (author.isBot()) return;

        String[] args = message.getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("c!ping")) {
            channel.sendMessage("Pong!").queue();
        } else if (args[0].equalsIgnoreCase("c!oi")) {
            channel.sendMessage("Olá, " + author.getName() + "!").queue();
        } else if (args[0].equalsIgnoreCase("c!infousuario")) {
            if (args.length < 2) {
                channel.sendMessage("Por favor, mencione um usuário para obter informações.").queue();
                return;
            }

            String targetUserId = args[1].replaceAll("<@!?(\\d+)>", "$1");
            Member targetMember = event.getGuild().getMemberById(targetUserId);

            if (targetMember != null) {
                User targetUser = targetMember.getUser();
                channel.sendMessage("Informações sobre " + targetUser.getName() + ":")
                        .append("\nID: ").append(targetUser.getId())
                        .append("\nApelido: ").append(targetMember.getNickname() != null ? targetMember.getNickname() : "Nenhum")
                        .append("\nStatus: ").append(targetMember.getOnlineStatus())
                        .append("\nAtividade: ").append(targetMember.getActivities().isEmpty() ? "Nenhuma" : targetMember.getActivities().get(0).getName())
                        .queue();
            } else {
                channel.sendMessage("Usuário não encontrado.").queue();
            }
        }
    }
}
