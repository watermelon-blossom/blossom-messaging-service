package com.watermelon.chat.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ContentType;

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
