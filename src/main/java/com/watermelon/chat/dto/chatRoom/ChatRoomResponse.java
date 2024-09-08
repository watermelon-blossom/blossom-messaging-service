package com.watermelon.chat.dto.chatRoom;

import java.time.LocalDateTime;

import com.watermelon.chat.domain.mongo.chatRoom.ChatRoom;
import com.watermelon.chat.domain.mongo.chatRoom.ChatUsers;
import com.watermelon.chat.domain.mongo.chatRoom.RoomStatus;

public record ChatRoomResponse(
	String roomId,
	ChatUsers chatUsers,
	String lastMessage,
	LocalDateTime lastMessageTime,
	Integer unreadMessageCount,
	RoomStatus roomStatus
) {
	//create Paging response of CharRoomResponse from ChatRoom
	public static ChatRoomResponse from(ChatRoom chatRoom, Integer unreadMessageCount) {
		return new ChatRoomResponse(chatRoom.getId(),
			chatRoom.getChatUsers(),
			chatRoom.getLatestChat().getContent(),
			chatRoom.getLatestChat().getSendDate(),
			unreadMessageCount,
			chatRoom.getRoomStatus());
	}
}
