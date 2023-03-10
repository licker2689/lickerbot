package org.example;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReadyListener implements EventListener, java.util.EventListener {
    public static void main(String[] args)
            throws InterruptedException {
        // Note: It is important to register your ReadyListener before building
        JDA jda = JDABuilder
                .createDefault("MTAzNjgxNjMzMDU3NTMzNTUzNA.GFHzFW.N6T5IlSINGJepBkIRQls3VQ8COgdIHTpZlqmzA")
                .addEventListeners(new ReadyListener()).enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS).enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS).enableIntents(GatewayIntent.GUILD_BANS)
                .enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING)
                .setActivity(Activity.listening("official?????? ?????????"))
                .build();

        // optionally block until JDA is ready
        jda.awaitReady();

        jda.updateCommands().addCommands(
                Commands.slash("??????", "Calculate ping of the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_VOICE_PERMISSIONS)),
                Commands.slash("??????", "Calculate ping of the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_VOICE_PERMISSIONS)),/*,
                Commands.slash("??????", "????????? ????????????").addOption(OptionType.STRING, "id", "????????? ??????")*/
                Commands.slash("????????????", "?????? ????????? ?????? ????????? ???????????????!")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_CHANNEL_PERMISSIONS))
                        .addOption(OptionType.STRING, "????????????", "??????????????? ???????????????!",true)
        ).queue();

    }

    boolean flag = false;

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println("API is ready!");
        }
        if (event instanceof GuildMemberJoinEvent) {

            if (((GuildMemberJoinEvent) event).getGuild().getName().equals("???????????????")) {
                System.out.println(((GuildMemberJoinEvent) event).getMember().getUser().getName() + ((GuildMemberJoinEvent) event).getGuild());
                Role r = ((GuildMemberJoinEvent) event).getGuild().getRoleById("1019811149602107402");
                User u = ((GuildMemberJoinEvent) event).getUser();
                ((GuildMemberJoinEvent) event).getGuild().addRoleToMember(u, r).complete();
            }
        }
        if (event instanceof MessageReceivedEvent) {
            Message m = ((MessageReceivedEvent) event).getMessage();
            Role r = ((MessageReceivedEvent) event).getGuild().getRoleById("1019811149602107402");
            if (!m.getAuthor().getName().equals("?????????")) {
                System.out.println(m.getAuthor().getName() + " : " + m.getContentDisplay() + "(???)?????? " + m.getChannel().getName() + "?????? ???????????????.");
                logment(m, event);
            }
            if (!Objects.equals(m.getGuild().getName(), "???????????????") || !Objects.equals(m.getGuild().getName(), "lickerbot log")) {
                if (m.getContentDisplay().contains("?????????")) {
                    if (isOwner(m.getAuthor().getName()) || isSeverOwner(((MessageReceivedEvent) event).getMember().getGuild().getOwnerId(), m.getAuthor())) {
                        m.getChannel().sendMessage("??? ???????????????? ??????????").queue();
                    } else {
                        m.getChannel().sendMessage("??? ???????????????? ?????????").queue(message -> message.getChannel().sendTyping().delay(2,TimeUnit.SECONDS).and(message.editMessage("???????????? ????????????!")).queue());
                    }
                    flag = true;
                }
                if (m.getContentDisplay().equals("????????????") && flag) {
                    if (isOwner(m.getAuthor().getName())) {
                        m.getChannel().sendMessage("?????? ??????????????? ?????????, ??? ????????? ????????? ???????????? ?????????! ????????????!").queue();
                    } else {
                        m.getChannel().sendMessage("??????, ??? ???!, ????????? ?????? ????????????!").queue();
                    }

                    flag = false;
                }
                if (m.getContentDisplay().equals("??????!") && flag) {
                    if (isOwner(m.getAuthor().getName())) {
                        m.getChannel().sendMessage("???????????????! ??????! ??????!").queue();
                    } else {
                        m.getChannel().sendMessage("`??????, ?????? ????????? ????????????!` ?????? ???????").queue();
                    }

                    flag = false;
                }
            }
            if (m.getContentDisplay().contains("????????????") && !m.getAuthor().isBot()) {
                sendLaugh(m);
            }
            if (m.getGuild().getName().equals("???????????????") && !m.getMember().getRoles().contains(r)) {
                User u = ((MessageReceivedEvent) event).getAuthor();
                ((MessageReceivedEvent) event).getGuild().addRoleToMember(u, r).complete();
            }
            if (m.getGuild().getName().equals("DPP ????????????")) {
                VoiceChannel vch = ((MessageReceivedEvent) event).getGuild().getVoiceChannelById("987294824619200566");
                assert vch != null;
                int psn = Integer.parseInt(vch.getName().replaceAll("\\D+", ""));
                int i = m.getGuild().getMemberCount();
                if (!(psn == i)) {
                    System.out.println("check1000");
                    String s = String.valueOf(i);
                    vch.getManager().setName("?????? : " + i).queue();
                }
            }
        }
        if (event instanceof MessageReactionAddEvent) {
            MessageReaction m = ((MessageReactionAddEvent) event).getReaction();
            String pn = ((MessageReactionAddEvent) event).getUser().getId();
            TextChannel tch = ((MessageReactionAddEvent) event).getGuild().getTextChannelById("1022854891011579955");

            if (m.getMessageId().equals("921344892679110666")) {
                System.out.println("Reaction event");
                tch.sendMessage("<@" + pn + ">" + "??? ????????? ????????? ???????????????! ????????? ???????????? ???????????? ????????????!").queue();
            }
        }
        if (event instanceof GuildMemberRemoveEvent) {

            if (((GuildMemberRemoveEvent) event).getGuild().getName().equals("DPP ????????????")) {
                VoiceChannel vch = ((GuildMemberRemoveEvent) event).getGuild().getVoiceChannelById("987294824619200566");
                int psn = Integer.parseInt(vch.getName().replaceAll("\\D+", ""));
                psn = psn - 1;
                String s = String.valueOf(psn);
                vch.getManager().setName("?????? : " + psn);
            }
        }
        if (event instanceof SlashCommandInteractionEvent) {
            if (((SlashCommandInteractionEvent) event).getMember().getUser().isBot()) return;
            switch (((SlashCommandInteractionEvent) event).getName()) {
                case "??????":
                    Guild guild = ((SlashCommandInteractionEvent) event).getGuild();
                    VoiceChannel channel = ((SlashCommandInteractionEvent) event).getMember().getVoiceState().getChannel().asVoiceChannel();
                    AudioManager manager = guild.getAudioManager();
                    if (channel == null) {
                        break;
                    }

                    manager.closeAudioConnection();
                    break;
                case "??????":
                    Guild cguild = ((SlashCommandInteractionEvent) event).getGuild();
                    VoiceChannel cchannel = ((SlashCommandInteractionEvent) event).getMember().getVoiceState().getChannel().asVoiceChannel();
                    AudioManager cmanager = cguild.getAudioManager();
                    if (cchannel == null) {
                        break;
                    }
                    cmanager.openAudioConnection(cchannel);

                    break;
               /* case "??????":
                    AudioManager pmanager = ((SlashCommandInteractionEvent) event).getGuild().getAudioManager();

                    String path = String.valueOf(((SlashCommandInteractionEvent) event).getOption("id"));
                    AudioPlayerManager pm = new DefaultAudioPlayerManager();
                    YoutubeAudioSourceManager yam = new YoutubeAudioSourceManager();

                    AudioPlayer audioPlayer = pm.createPlayer();
                    YoutubeAudioTrack tr = (YoutubeAudioTrack) yam.loadTrackWithVideoId(path, false);
                    pmanager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
                    ((SlashCommandInteractionEvent) event).getChannel().sendMessage(tr.getSourceManager().getSourceName());
                    audioPlayer.startTrack(tr,false);*/
                case "????????????":
                    Guild gk = ((SlashCommandInteractionEvent) event).getGuild();
                    ((SlashCommandInteractionEvent) event).getGuild().createTextChannel(Objects
                                    .requireNonNull(Objects.requireNonNull(((SlashCommandInteractionEvent) event).getOption("????????????")).getAsString()))
                            .addPermissionOverride(gk.getPublicRole(), EnumSet.of(Permission.MESSAGE_SEND),EnumSet.of(Permission.MESSAGE_HISTORY,Permission.VIEW_AUDIT_LOGS, Permission.MESSAGE_HISTORY))
                            .complete();
                    ((SlashCommandInteractionEvent) event).reply(((((SlashCommandInteractionEvent) event).getOption("????????????")).getAsString()) + " ????????? ??????????????? ?????????????????????!").queue();

                default:
                    throw new IllegalStateException("Unexpected value: " + ((SlashCommandInteractionEvent) event).getName());

            }
        }
    }

    public boolean isOwner(String name) {
        return name.equals("nleader22 [  ]") || name.equals("licker2689");
    }

    public boolean isSeverOwner(String s, User u) {
        return s.equals(u.getAvatarId());
    }

    public void sendLaugh(Message m) {
        if (SendLaugh.getCoolLaugh() == true) {
            Random r = new Random();
            int i = r.nextInt(11);
            if (i == 1) {
                m.getChannel().sendMessage("?????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 2) {
                m.getChannel().sendMessage("????????????????????????????????????????????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 3) {
                m.getChannel().sendMessage("?????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 4) {
                m.getChannel().sendMessage("???????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 5) {
                m.getChannel().sendMessage("?????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 6) {
                m.getChannel().sendMessage("????????????????????????????????????????????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 7) {
                m.getChannel().sendMessage("?????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 8) {
                m.getChannel().sendMessage("???????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 9) {
                m.getChannel().sendMessage("?????????????????????").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 10) {
                m.getChannel().sendMessage("??? ???????????? ???????????????").queue();
                SendLaugh.setCoolLaugh();
            }
        }
    }

    public void logment(Message m, GenericEvent event) {
        Guild g = Objects.requireNonNull(event.getJDA().getGuildById("1049213626780749855"));
        if (g.getTextChannelsByName(m.getGuild().getName().replaceAll(" ","-").replaceAll("\\.",""),true).size() == 0){
            g.createTextChannel((m.getGuild().getName())).queue();
        }
        g.getTextChannelsByName(m.getGuild().getName().replaceAll(" ","-").replaceAll("\\.",""),true).get(0).sendMessage(m.getAuthor().getName() + ": "+ m.getContentRaw() +"(???)??????"+ m.getChannel().getName() + "?????? ???????????????.").queue();

        }


}

