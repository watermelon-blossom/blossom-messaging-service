package com.watermelon.chat.dto.chatRoom;

public record GetChatRoomByLastMessageId(
	String roomId,
	String lastMessageId,
	String limit
) {
}
