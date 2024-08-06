package com.watermelon.message.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ContentType;

public record ChatResponse(
	String roomId,
	String senderId,
	String content,
	ContentType contentType,
	boolean hasRead,
	LocalDateTime sendDate
) {
	public static ChatResponse from(Chat chat) {
		return new ChatResponse(
			chat.getRoomId(),
			chat.getSenderId(),
			chat.getContent(),
			chat.getContentType(),
			chat.isHasRead(),
			chat.getSendDate());
	}

}
