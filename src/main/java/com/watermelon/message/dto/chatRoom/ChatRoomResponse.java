package com.watermelon.message.dto.chatRoom;

import java.time.LocalDateTime;

import com.watermelon.message.domain.mongo.chatRoom.ChatRoom;
import com.watermelon.message.domain.mongo.chatRoom.ChatUsers;
import com.watermelon.message.domain.mongo.chatRoom.RoomStatus;

public record ChatRoomResponse(
	String roomId,
	ChatUsers chatUsers,
	String lastMessage,
	LocalDateTime lastMessageTime,
	Integer unreadMessageCount,
	RoomStatus roomStatus
) {
	//create Paging response of CharRoomResponse from ChatRoom
	public static ChatRoomResponse from(ChatRoom chatRoom, String lastMessage,
		LocalDateTime lastMessageTime, Integer unreadMessageCount) {
		return new ChatRoomResponse(chatRoom.getId(),
			chatRoom.getChatUsers(),
			lastMessage,
			lastMessageTime,
			unreadMessageCount,
			chatRoom.getRoomStatus());
	}
}
