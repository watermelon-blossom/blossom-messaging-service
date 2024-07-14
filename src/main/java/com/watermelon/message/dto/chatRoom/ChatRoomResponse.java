package com.watermelon.message.dto.chatRoom;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chatRoom.ChatUser;
import com.watermelon.message.domain.chatRoom.RoomStatus;

public record ChatRoomResponse(
	String id,
	ChatUser chatUser,
	String lastMessage,
	LocalDateTime lastMessageTime,
	Integer unreadMessageCount,
	RoomStatus roomStatus
) {
}
