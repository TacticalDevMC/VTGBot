package me.vortexgames.vtgbot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.vortexgames.vtgbot.Constants;
import me.vortexgames.vtgbot.music.GuildMusicManager;
import me.vortexgames.vtgbot.music.PlayerManager;
import me.vortexgames.vtgbot.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class VolumeCommand implements ICommand {


    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;
        AudioManager audioManager = event.getGuild().getAudioManager();

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!event.getAuthor().getId().equals(Constants.OWNER)) {
            event.getChannel().sendMessage("Deze functie is momenteel alleen toegankelijk voor de **BotOwner**.").queue();
        } else {
            if (!voiceChannel.getMembers().contains(event.getMember())) {
                channel.sendMessage("U moet in de zelfde voicechannel als de bot zitten.").queue();
                return;
            }

            if (args.isEmpty()) {
                channel.sendMessage("Geef alstublieft een volume mee, max toegewezen volume is **150**.").queue();
                return;
            }
            
            if (args.size() > 150) {
                System.out.print("test");
                channel.sendMessage("U kunt het volume niet meer dan **150** zetten.").queue();
                return;
            }

            String input = String.join(" ", args);

            player.setVolume(Integer.valueOf(input));

            channel.sendMessage("De volume is aangepast naar **" + input + "**.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Change the current song's volume";
    }

    @Override
    public String getInvoke() {
        return "volume";
    }
}
