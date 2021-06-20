package ru.sunoplyaandesin.simplemessenger.service.impl;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.exception.YoutubeNotFoundException;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;
import ru.sunoplyaandesin.simplemessenger.service.command.CommandContainer;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class YBotServiceImpl implements YBotService {

    private final SimpMessagingTemplate template;

    private final YouTube youtube;

    private final UserDTO yBot;

    private final RoomDTO yBotRoom;

    private final UserDTO admin;

    private final CommandContainer commandContainer = new CommandContainer(this);

    private final static String VIDEO_URL_START = "https://www.youtube.com/watch?v=";

    @Value("${youtube.apikey}")
    private String API_KEY;

    @Value("${command.prefix}")
    private String COMMAND_PREFIX;

    @Override
    public MessageDTO channelInfo(String title) {
        sendMessageToTopic(MessageDTO.builder()
                .user(yBot)
                .text("Channel title: " + title)
                .createdDate(new Date())
                .build());

        String channelId = getChannelId(title);
        Channel channel = getChannelByChannelId(channelId);

        MessageDTO lastFiveVideoLinks = MessageDTO.builder()
                .text(getLastFiveVideoLinks(channel))
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();

        sendMessageToTopic(lastFiveVideoLinks);

        return lastFiveVideoLinks;
    }

    @Override
    public List<MessageDTO> videoCommentRandom(String channelTitle, String videoTitle) {
        sendMessageToTopic(MessageDTO.builder()
                .user(yBot)
                .text("Channel title: " + channelTitle)
                .createdDate(new Date())
                .build());
        sendMessageToTopic(MessageDTO.builder()
                .user(yBot)
                .text("Video title: " + videoTitle)
                .createdDate(new Date())
                .build());

        String channelId = getChannelId(channelTitle);
        Channel channel = getChannelByChannelId(channelId);
        PlaylistItem videoByTitle = findVideoByTitle(channel, videoTitle);

        if (videoByTitle != null) {
            List<MessageDTO> randomComment = getRandomComment(videoByTitle);
            sendMessageToTopic(MessageDTO.builder()
                    .user(yBot)
                    .text("Author: " + randomComment.get(0).getText())
                    .createdDate(new Date())
                    .build());
            sendMessageToTopic(MessageDTO.builder()
                    .user(yBot)
                    .text("Comment: " + randomComment.get(1).getText())
                    .createdDate(new Date())
                    .build());
            return randomComment;
        }
        throw new YoutubeNotFoundException(videoTitle);
    }

    @Override
    public void help(String helpMessage) {
        sendMessageToTopic(MessageDTO.builder()
                .user(yBot)
                .text(helpMessage)
                .createdDate(new Date())
                .build());
    }

    @Override
    public void sendMessage(String text) {
        MessageDTO receivedMessage = MessageDTO.builder()
                .room(yBotRoom)
                .user(admin)
                .text(text)
                .createdDate(new Date())
                .build();

        sendMessageToTopic(receivedMessage);

        if (text.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = text.trim().split(" ")[0].toLowerCase();
            Command command = commandContainer.retrieveCommand(commandIdentifier);
            command.execute(text
                    .replace(commandIdentifier, "").trim());
        }
    }

    private void sendMessageToTopic(MessageDTO messageDTO) {
        template.convertAndSend("/topic/greetings", messageDTO);
    }

    private String getChannelId(String title) {
        try {
            YouTube.Search.List search = youtube.search().list("snippet");
            search.setQ(title);
            search.setKey(API_KEY);
            SearchListResponse searchListResponse = search.execute();
            if (searchListResponse.getItems().isEmpty()) throw new YoutubeNotFoundException(title);
            String channelId = searchListResponse.getItems().get(0).getSnippet().getChannelId();
            return channelId;
        } catch (IOException ioException) {
            throw new YoutubeNotFoundException(title);
        }
    }

    private Channel getChannelByChannelId(String channelId) {
        try {
            YouTube.Channels.List channelRequest = youtube.channels().list("id,snippet,contentDetails");
            channelRequest.setKey(API_KEY);
            channelRequest.setId(channelId);
            channelRequest.setFields("items/contentDetails,items/snippet/title,nextPageToken,pageInfo");
            return channelRequest.execute().getItems().get(0);
        } catch (IOException ioException) {
            throw new YoutubeNotFoundException(channelId);
        }
    }

    private String getLastFiveVideoLinks(Channel channel) {

        List<PlaylistItem> playlistItemList = new ArrayList<>();

        List<PlaylistItem> playlistItemResult = getFiveVideo(channel, "").getItems();

        playlistItemList.addAll(playlistItemResult.subList(0, 5));

        StringBuilder message = new StringBuilder();

        playlistItemList.forEach(item -> {
            String link = VIDEO_URL_START + item.getContentDetails().getVideoId();
            message.append("<a href=\"")
                    .append(link).append("\" target=_blank>").append(link).append("</a> ");
        });
        return message.toString();
    }

    private PlaylistItem findVideoByTitle(Channel channel, String videoTitle) {
        String nextToken = "";
        PlaylistItem video;
        do {
            PlaylistItemListResponse fiveVideo = getFiveVideo(channel, nextToken);
            List<PlaylistItem> videos = fiveVideo.getItems();
            Optional<PlaylistItem> searchVideo = videos.stream().filter(v -> v.getSnippet()
                    .getTitle().equals(videoTitle)).findAny();

            if (searchVideo.isPresent()) {
                return searchVideo.get();
            }
            nextToken = fiveVideo.getNextPageToken();
        } while (nextToken != null);
        return null;
    }

    private PlaylistItemListResponse getFiveVideo(Channel channel, String token) {
        try {
            String uploadPlaylistId =
                    channel.getContentDetails().getRelatedPlaylists().getUploads();
            List<PlaylistItem> playlistItemList = new ArrayList<>();
            YouTube.PlaylistItems.List playlistItemRequest =
                    youtube.playlistItems().list("id,contentDetails,snippet");
            playlistItemRequest.setKey(API_KEY);
            playlistItemRequest.setPlaylistId(uploadPlaylistId);
            playlistItemRequest.setMaxResults(5L);
            playlistItemRequest.setFields(
                    "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");
            playlistItemRequest.setPageToken(token);
            return playlistItemRequest.execute();
        } catch (IOException ioException) {
            throw new YoutubeNotFoundException(channel.getSnippet().getTitle());
        }
    }

    private List<MessageDTO> getRandomComment(PlaylistItem video) {
        CommentThreadListResponse videoCommentsListResponse = getVideoCommentsListResponse(video);

        List<CommentThread> videoComments = videoCommentsListResponse.getItems();

        int randomIndex = new Random().nextInt(videoComments.size());
        CommentThread randomComment = videoComments.get(randomIndex);
        CommentSnippet snippet = randomComment.getSnippet().getTopLevelComment().getSnippet();

        MessageDTO author = MessageDTO.builder().text(snippet.getAuthorDisplayName())
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();

        MessageDTO comment = MessageDTO.builder().text(snippet.getTextDisplay())
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();

        List<MessageDTO> authorAndComment = new ArrayList<>();
        authorAndComment.add(author);
        authorAndComment.add(comment);
        return authorAndComment;
    }

    private CommentThreadListResponse getVideoCommentsListResponse(PlaylistItem video) {
        try {
            CommentThreadListResponse videoCommentsListResponse = youtube.commentThreads()
                    .list("snippet").setVideoId(video.getContentDetails().getVideoId())
                    .setTextFormat("plainText")
                    .setKey(API_KEY)
                    .execute();
            return videoCommentsListResponse;
        } catch (IOException ioException) {
            throw new YoutubeNotFoundException(video.getSnippet().getTitle());
        }
    }
}