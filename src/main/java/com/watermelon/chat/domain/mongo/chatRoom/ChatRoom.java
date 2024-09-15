package com.watermelon.chat.domain.mongo.chatRoom;

import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ChatReadService;
import com.watermelon.chat.dto.chat.ChatResponse;
import com.watermelon.chat.global.common.BaseEntity;
import com.watermelon.chat.global.error.ApplicationException;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.watermelon.chat.domain.mongo.chatRoom.RoomStatus.ACTIVE;
import static com.watermelon.chat.global.error.ErrorType.NO_SUCH_USER_IN_CHATROOM;

@Document(collection = "chatRooms")
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    private String id;
    private ChatUsers chatUsers;
    private RoomStatus roomStatus;

    @DBRef
    private List<Chat> chats = new ArrayList<>();

    public static ChatRoom createChatRoom(ChatUsers users) {
        return ChatRoom.builder()
                .chatUsers(users)
                .roomStatus(ACTIVE)
                .build();
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    void addUser(ChatUser user) {
        this.chatUsers.addUser(user);
    }

    public void deleteRoom() {
        this.roomStatus = RoomStatus.DELETED;
    }

    public boolean isUserInRoom(String userId) {
        List<ChatUser> users = chatUsers.getUsers();
        return users.stream()
                .map(ChatUser::getUserId)
                .anyMatch(id -> id.equals(userId));
    }

    public Chat getLatestChat() {
        return this.chats.isEmpty() ? new Chat() : this.chats.get(0);
    }

    public long getUnreadCountOf(ChatReadService chatReadService, String userId) {
        validateUserInRoom(userId);
        return chatReadService.getUnReadInRoomWithRoomIdAndUserId(this.id, userId);
    }

    private void validateUserInRoom(String userId) {
        if (!isUserInRoom(userId)) throw new ApplicationException(NO_SUCH_USER_IN_CHATROOM);
    }

    public List<ChatResponse> get20LatestChat() {
        return this.chats.stream()
                .map(ChatResponse::from)
                .limit(20)
                .toList();
    }

    public void readRoomByUser(ChatReadService chatReadService, String userId) {
        validateUserInRoom(userId);
        chatReadService.readRoom(this.id, userId);
    }


}
