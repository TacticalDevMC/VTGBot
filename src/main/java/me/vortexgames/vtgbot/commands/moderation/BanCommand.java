package me.vortexgames.vtgbot.commands.moderation;

import me.vortexgames.vtgbot.Constants;
import me.vortexgames.vtgbot.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BanCommand implements ICommand {


    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (mentionedMembers.isEmpty() || args.size() < 2) {
            channel.sendMessageFormat("Er missen argumenten").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.BAN_MEMBERS) && !member.canInteract(target)) {
            channel.sendMessage("U hebt geen permissions voor dit command.").queue();
            return;
        }
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) && !selfMember.canInteract(target)) {
            channel.sendMessage("Ik kan deze user niet bannen, of ik heb niet de juiste permissions.").queue();
            return;
        }

        event.getGuild().getController().ban(target, 1)
                .reason(String.format("Ban door: %#s, met reden: %s", event.getAuthor(), reason)).queue();
        channel.sendMessage("Succes!").queue();

    }

    @Override
    public String getHelp() {
        return "Bans a user from the server.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <user> <reason>`";
    }

    @Override
    public String getInvoke() {
        return "ban";
    }
}
