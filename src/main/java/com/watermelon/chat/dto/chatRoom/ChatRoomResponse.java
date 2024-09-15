package com.watermelon.chat.dto.chatRoom;

import com.watermelon.chat.domain.mongo.chatRoom.ChatRoom;
import com.watermelon.chat.domain.mongo.chatRoom.ChatUsers;
import com.watermelon.chat.domain.mongo.chatRoom.RoomStatus;

import java.time.LocalDateTime;

public record ChatRoomResponse(
        String roomId,
        ChatUsers chatUsers,
        String lastMessage,
        LocalDateTime lastMessageTime,
        Long unreadMessageCount,
        RoomStatus roomStatus
) {
    //create Paging response of CharRoomResponse from ChatRoom
    public static ChatRoomResponse from(ChatRoom chatRoom, long unreadMessageCount) {
        return new ChatRoomResponse(chatRoom.getId(),
                chatRoom.getChatUsers(),
                chatRoom.getLatestChat().getContent(),
                chatRoom.getLatestChat().getSendDate(),
                unreadMessageCount,
                chatRoom.getRoomStatus());
    }

}
