package me.vortexgames.vtgbot.commands;

import me.vortexgames.vtgbot.objects.ICommand;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BanJasper implements ICommand {


    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        Member target = mentionedMembers.get(0);

//        event.getGuild().getController().ban(target, 1).reason("Schatjeee").queue();

        Role role = event.getGuild().getRoleById("593074171269873694");

        event.getGuild().getController().addSingleRoleToMember(target, role).queue();

    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return "banjasper";
    }
}
