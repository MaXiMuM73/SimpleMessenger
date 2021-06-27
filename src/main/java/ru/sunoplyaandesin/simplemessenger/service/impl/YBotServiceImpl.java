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
import ru.sunoplyaandesin.simplemessenger.service.RoomService;
import ru.sunoplyaandesin.simplemessenger.service.UserService;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;
import ru.sunoplyaandesin.simplemessenger.service.command.CommandContainer;
import ru.sunoplyaandesin.simplemessenger.service.command.CommandName;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YBotServiceImpl implements YBotService {

    private final RoomService roomService;

    private final UserService userService;

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
    public List<MessageDTO> channelInfo(String title) {
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
        return Collections.singletonList(lastFiveVideoLinks);
    }

    @Override
    public List<MessageDTO> videoCommentRandom(String channelTitle, String videoTitle) {
//        sendMessageToTopic(MessageDTO.builder()
//                .user(yBot)
//                .text("Channel title: " + channelTitle)
//                .createdDate(new Date())
//                .build());
//        sendMessageToTopic(MessageDTO.builder()
//                .user(yBot)
//                .text("Video title: " + videoTitle)
//                .createdDate(new Date())
//                .build());


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
    public List<MessageDTO> getVideoLinkLikeView(String channelTitle, String videoTitle) {
        String channelId = getChannelId(channelTitle);
        Channel channel = getChannelByChannelId(channelId);
        PlaylistItem videoByTitle = findVideoByTitle(channel, videoTitle);
        if (videoByTitle != null) {
            String videoId = videoByTitle.getContentDetails().getVideoId();
            String videoLink = VIDEO_URL_START + videoId;
            String text = videoLink;
            try {
                YouTube.Videos.List request = youtube.videos()
                        .list("snippet,contentDetails,statistics")
                        .setKey(API_KEY);
                VideoListResponse response = request.setId(videoId).execute();
                BigInteger likeCount = response.getItems().get(0).getStatistics().getLikeCount();
                BigInteger viewCount = response.getItems().get(0).getStatistics().getViewCount();
                text += " likes: " + likeCount + " views: " + viewCount;
            } catch (IOException ioException) {
                throw new YoutubeNotFoundException(videoId);
            }
            MessageDTO videoLinkLikesViewMessage = MessageDTO.builder()
                    .text(text)
                    .user(yBot)
                    .room(yBotRoom)
                    .createdDate(new Date())
                    .build();
            return Collections.singletonList(videoLinkLikesViewMessage);
        }
        throw new YoutubeNotFoundException(videoTitle);
    }

    @Override
    public List<MessageDTO> help(String helpMessage) {
        MessageDTO help = MessageDTO.builder()
                .user(yBot)
                .room(yBotRoom)
                .text(helpMessage)
                .createdDate(new Date())
                .build();

        sendMessageToTopic(help);

        return Collections.singletonList(help);
    }

    @Override
    public String sendMessage(String text) {
        MessageDTO receivedMessage = MessageDTO.builder()
                .room(yBotRoom)
                .user(admin)
                .text(text)
                .createdDate(new Date())
                .build();

        sendMessageToTopic(receivedMessage);

        if (text.startsWith(COMMAND_PREFIX)) {
            List<String> commands = Arrays.stream(CommandName.values())
                    .map(CommandName::getCommandName)
                    .collect(Collectors.toList());

            String commandIdentifier = commands.stream()
                    .filter(text::contains).findAny()
                    .orElseGet(() -> text).toLowerCase();

            Command command = commandContainer.retrieveCommand(commandIdentifier);
            command.execute(text.replace(commandIdentifier, "").trim(), 1L);
        }
        return text;
    }

    @Override
    public List<MessageDTO> processCommand(String commandText, long userId) {
        List<String> commands = Arrays.stream(CommandName.values())
                .map(CommandName::getCommandName)
                .collect(Collectors.toList());

        String commandIdentifier = commands.stream()
                .filter(commandText::contains).findAny()
                .orElseGet(() -> commandText).toLowerCase();

        Command command = commandContainer.retrieveCommand(commandIdentifier);

        return command.execute(commandText.replace(commandIdentifier, "").trim(), userId);
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
                    "items(contentDetails/videoId,snippet/title,snippet/publishedAt), nextPageToken, pageInfo");
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

    @Override
    public List<MessageDTO> createRoom(long userId, String roomTitle, boolean privateRoom) {
        RoomDTO roomDTO = RoomDTO.builder()
                .title(roomTitle)
                .privateRoom(privateRoom)
                .createdDate(new Date())
                .build();

        roomService.create(roomDTO, 1);
        sendMessage("Room created: " + roomDTO.getTitle());
        MessageDTO createRoomMessage = MessageDTO.builder()
                .text("Room created: " + roomDTO.getTitle())
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(createRoomMessage);
    }

    @Override
    public List<MessageDTO> deleteRoom(long userId, String roomTitle) {
        roomService.delete(roomTitle, userId);
        MessageDTO deleteRoomMessage = MessageDTO.builder()
                .text("Room deleted: " + roomTitle)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(deleteRoomMessage);
    }

    @Override
    public List<MessageDTO> renameRoom(long userId, String roomTitle, String newTitle) {
        roomService.rename(userId, roomTitle, newTitle);
        MessageDTO renameRoomMessage = MessageDTO.builder()
                .text("Room renamed: " + roomTitle + ". New title: " + newTitle)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(renameRoomMessage);
    }

    @Override
    public List<MessageDTO> unknownCommand() {
        MessageDTO unknownCommandMessage = MessageDTO.builder()
                .text("Unknown command. //help for command information.")
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(unknownCommandMessage);
    }

    @Override
    public List<MessageDTO> connectToRoom(long userId, String roomTitle, String userNameToConnect) {
        sendMessage("User connected.");
        String text;
        if (userNameToConnect.equals("")) {
            roomService.connect(userId, roomTitle);
            text = "User with id " + userId;
        } else {
            roomService.connect(userNameToConnect, roomTitle);
            text = "User with name " + userNameToConnect;
        }

        MessageDTO connectToRoomMessage = MessageDTO.builder()
                .text(text + " connected to Room: " + roomTitle)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(connectToRoomMessage);
    }

    @Override
    public List<MessageDTO> disconnectFromRoom(long userId, String roomTitle,
                                               String userNameToDisconnect, long banTime) {
        String text = "User: " + userNameToDisconnect;
        if (banTime != 0) {
            text += " banned for " + banTime + " minutes.";
        } else {
            text += " disconnected from room: " + roomTitle;
        }
        roomService.disconnect(roomTitle, userNameToDisconnect, banTime, userId);
        MessageDTO disconnectFromRoomMessage = MessageDTO.builder()
                .text(text)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(disconnectFromRoomMessage);
    }

    @Override
    public List<MessageDTO> renameUser(long userId, String userToRename, String newName) {
        String text = "User: " + userToRename + " renamed. New name: " + newName;
        userService.rename(userId, userToRename, newName);
        MessageDTO renameMessage = MessageDTO.builder()
                .text(text)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(renameMessage);
    }

    @Override
    public List<MessageDTO> assignRoleToUser(long userId, String userToAssign, String tag) {
        String text = "User: " + userToAssign;
        if (tag.equals("-n")) {
            text += " moderator.";
        } else {
            text += " user.";
        }
        userService.assignRoleToUser(userId, "testroom", userToAssign, tag);
        MessageDTO assignMessage = MessageDTO.builder()
                .text(text)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(assignMessage);
    }

    @Override
    public List<MessageDTO> banUser(long userId, String userToBan, long banTime) {
        String text = "User: " + userToBan;
        if (banTime != 0) {
            text += " banned for " + banTime + " minutes.";
        } else {
            text += " disconnected from all rooms.";
        }
        userService.banUser(userId, userToBan, banTime);
        MessageDTO banMessage = MessageDTO.builder()
                .text(text)
                .user(yBot)
                .room(yBotRoom)
                .createdDate(new Date())
                .build();
        return Collections.singletonList(banMessage);
    }
}