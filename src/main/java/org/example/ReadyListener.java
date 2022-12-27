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
                .setActivity(Activity.listening("official히게 단디즘"))
                .build();

        // optionally block until JDA is ready
        jda.awaitReady();

        jda.updateCommands().addCommands(
                Commands.slash("입장", "Calculate ping of the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_VOICE_PERMISSIONS)),
                Commands.slash("퇴장", "Calculate ping of the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_VOICE_PERMISSIONS)),/*,
                Commands.slash("재생", "노래를 틀어줘요").addOption(OptionType.STRING, "id", "유튜브 링크")*/
                Commands.slash("채널생성", "비밀 얘기를 적는 채널을 만들어줘요!")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ALL_CHANNEL_PERMISSIONS))
                        .addOption(OptionType.STRING, "채널이름", "채널이름을 적어주세요!",true)
        ).queue();

    }

    boolean flag = false;

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println("API is ready!");
        }
        if (event instanceof GuildMemberJoinEvent) {

            if (((GuildMemberJoinEvent) event).getGuild().getName().equals("해마슈슈슉")) {
                System.out.println(((GuildMemberJoinEvent) event).getMember().getUser().getName() + ((GuildMemberJoinEvent) event).getGuild());
                Role r = ((GuildMemberJoinEvent) event).getGuild().getRoleById("1019811149602107402");
                User u = ((GuildMemberJoinEvent) event).getUser();
                ((GuildMemberJoinEvent) event).getGuild().addRoleToMember(u, r).complete();
            }
        }
        if (event instanceof MessageReceivedEvent) {
            Message m = ((MessageReceivedEvent) event).getMessage();
            Role r = ((MessageReceivedEvent) event).getGuild().getRoleById("1019811149602107402");
            if (!m.getAuthor().getName().equals("리꺼봇")) {
                System.out.println(m.getAuthor().getName() + " : " + m.getContentDisplay() + "(이)라고 " + m.getChannel().getName() + "에서 보냈습니다.");
                logment(m, event);
            }
            if (!Objects.equals(m.getGuild().getName(), "해마슈슈슉") || !Objects.equals(m.getGuild().getName(), "lickerbot log")) {
                if (m.getContentDisplay().contains("리꺼봇")) {
                    if (isOwner(m.getAuthor().getName()) || isSeverOwner(((MessageReceivedEvent) event).getMember().getGuild().getOwnerId(), m.getAuthor())) {
                        m.getChannel().sendMessage("왜 부르시나요? 주인님?").queue();
                    } else {
                        m.getChannel().sendMessage("왜 부르시나요? 주인님").queue(message -> message.getChannel().sendTyping().delay(2,TimeUnit.SECONDS).and(message.editMessage("주인님이 아니잖아!")).queue());
                    }
                    flag = true;
                }
                if (m.getContentDisplay().equals("코딩해줘") && flag) {
                    if (isOwner(m.getAuthor().getName())) {
                        m.getChannel().sendMessage("정말 죄송하지만 주인님, 제 능력이 안되서 어려울것 같아요! 죄송해요!").queue();
                    } else {
                        m.getChannel().sendMessage("싫어, 안 해!, 주인님 말만 들을꺼야!").queue();
                    }

                    flag = false;
                }
                if (m.getContentDisplay().equals("변신!") && flag) {
                    if (isOwner(m.getAuthor().getName())) {
                        m.getChannel().sendMessage("으아아아아! 몸이! 몸이!").queue();
                    } else {
                        m.getChannel().sendMessage("`삐빅, 명령 권한이 없습니다!` 랄까 나요?").queue();
                    }

                    flag = false;
                }
            }
            if (m.getContentDisplay().contains("ㅋㅋㅋㅋ") && !m.getAuthor().isBot()) {
                sendLaugh(m);
            }
            if (m.getGuild().getName().equals("해마슈슈슉") && !m.getMember().getRoles().contains(r)) {
                User u = ((MessageReceivedEvent) event).getAuthor();
                ((MessageReceivedEvent) event).getGuild().addRoleToMember(u, r).complete();
            }
            if (m.getGuild().getName().equals("DPP 커뮤니티")) {
                VoiceChannel vch = ((MessageReceivedEvent) event).getGuild().getVoiceChannelById("987294824619200566");
                assert vch != null;
                int psn = Integer.parseInt(vch.getName().replaceAll("\\D+", ""));
                int i = m.getGuild().getMemberCount();
                if (!(psn == i)) {
                    System.out.println("check1000");
                    String s = String.valueOf(i);
                    vch.getManager().setName("인원 : " + i).queue();
                }
            }
        }
        if (event instanceof MessageReactionAddEvent) {
            MessageReaction m = ((MessageReactionAddEvent) event).getReaction();
            String pn = ((MessageReactionAddEvent) event).getUser().getId();
            TextChannel tch = ((MessageReactionAddEvent) event).getGuild().getTextChannelById("1022854891011579955");

            if (m.getMessageId().equals("921344892679110666")) {
                System.out.println("Reaction event");
                tch.sendMessage("<@" + pn + ">" + "님 인증해 주셔서 감사합니다! 서버에 오신것을 진심으로 환영해요!").queue();
            }
        }
        if (event instanceof GuildMemberRemoveEvent) {

            if (((GuildMemberRemoveEvent) event).getGuild().getName().equals("DPP 커뮤니티")) {
                VoiceChannel vch = ((GuildMemberRemoveEvent) event).getGuild().getVoiceChannelById("987294824619200566");
                int psn = Integer.parseInt(vch.getName().replaceAll("\\D+", ""));
                psn = psn - 1;
                String s = String.valueOf(psn);
                vch.getManager().setName("인원 : " + psn);
            }
        }
        if (event instanceof SlashCommandInteractionEvent) {
            if (((SlashCommandInteractionEvent) event).getMember().getUser().isBot()) return;
            switch (((SlashCommandInteractionEvent) event).getName()) {
                case "퇴장":
                    Guild guild = ((SlashCommandInteractionEvent) event).getGuild();
                    VoiceChannel channel = ((SlashCommandInteractionEvent) event).getMember().getVoiceState().getChannel().asVoiceChannel();
                    AudioManager manager = guild.getAudioManager();
                    if (channel == null) {
                        break;
                    }

                    manager.closeAudioConnection();
                    break;
                case "입장":
                    Guild cguild = ((SlashCommandInteractionEvent) event).getGuild();
                    VoiceChannel cchannel = ((SlashCommandInteractionEvent) event).getMember().getVoiceState().getChannel().asVoiceChannel();
                    AudioManager cmanager = cguild.getAudioManager();
                    if (cchannel == null) {
                        break;
                    }
                    cmanager.openAudioConnection(cchannel);

                    break;
               /* case "재생":
                    AudioManager pmanager = ((SlashCommandInteractionEvent) event).getGuild().getAudioManager();

                    String path = String.valueOf(((SlashCommandInteractionEvent) event).getOption("id"));
                    AudioPlayerManager pm = new DefaultAudioPlayerManager();
                    YoutubeAudioSourceManager yam = new YoutubeAudioSourceManager();

                    AudioPlayer audioPlayer = pm.createPlayer();
                    YoutubeAudioTrack tr = (YoutubeAudioTrack) yam.loadTrackWithVideoId(path, false);
                    pmanager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
                    ((SlashCommandInteractionEvent) event).getChannel().sendMessage(tr.getSourceManager().getSourceName());
                    audioPlayer.startTrack(tr,false);*/
                case "채널생성":
                    Guild gk = ((SlashCommandInteractionEvent) event).getGuild();
                    ((SlashCommandInteractionEvent) event).getGuild().createTextChannel(Objects
                                    .requireNonNull(Objects.requireNonNull(((SlashCommandInteractionEvent) event).getOption("채널이름")).getAsString()))
                            .addPermissionOverride(gk.getPublicRole(), EnumSet.of(Permission.MESSAGE_SEND),EnumSet.of(Permission.MESSAGE_HISTORY,Permission.VIEW_AUDIT_LOGS, Permission.MESSAGE_HISTORY))
                            .complete();
                    ((SlashCommandInteractionEvent) event).reply(((((SlashCommandInteractionEvent) event).getOption("채널이름")).getAsString()) + " 채널이 성공적으로 생성되었습니다!").queue();

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
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 2) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 3) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 4) {
                m.getChannel().sendMessage("겔겔겔겔겔").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 5) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 6) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 7) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 8) {
                m.getChannel().sendMessage("겔겔겔겔겔").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 9) {
                m.getChannel().sendMessage("ㅋㅋㅋㅋㅋㅋㅋ").queue();
                SendLaugh.setCoolLaugh();
            }
            if (i == 10) {
                m.getChannel().sendMessage("배 아프니까 그만웃겨요").queue();
                SendLaugh.setCoolLaugh();
            }
        }
    }

    public void logment(Message m, GenericEvent event) {
        Guild g = Objects.requireNonNull(event.getJDA().getGuildById("1049213626780749855"));
        if (g.getTextChannelsByName(m.getGuild().getName().replaceAll(" ","-").replaceAll("\\.",""),true).size() == 0){
            g.createTextChannel((m.getGuild().getName())).queue();
        }
        g.getTextChannelsByName(m.getGuild().getName().replaceAll(" ","-").replaceAll("\\.",""),true).get(0).sendMessage(m.getAuthor().getName() + ": "+ m.getContentRaw() +"(이)라고"+ m.getChannel().getName() + "에서 보냈습니다.").queue();

        }


}

