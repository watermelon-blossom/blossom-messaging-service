package com.watermelon.chat.dto.chatRoom;

public record GetChatRoomByLastChatId(
	String roomId,
    String lastChatId,
	String limit
) {
}
