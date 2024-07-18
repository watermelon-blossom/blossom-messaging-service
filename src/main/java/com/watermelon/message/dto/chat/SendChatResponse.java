package com.watermelon.message.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ContentType;

public record SendChatResponse(
	String roomId,
	String senderId,
	String content,
	ContentType contentType,
	boolean hasRead,
	LocalDateTime sendDate
) {
	public static SendChatResponse from(Chat chat) {
		return new SendChatResponse(
			chat.getRoomId(),
			chat.getSenderId(),
			chat.getContent(),
			chat.getContentType(),
			chat.isHasRead(),
			chat.getSendDate());
	}

}
