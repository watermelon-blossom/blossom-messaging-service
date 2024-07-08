package com.watermelon.message.dto.chatting;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chat.Chat;

public record SendChatResponse(
	Integer roomId,
	String senderId,
	String content,
	String contentType,
	long readCount,
	LocalDateTime sendDate
) {
	public static SendChatResponse from(Chat chatting) {
		return new SendChatResponse(
			chatting.getRoomId(),
			chatting.getSenderId(),
			chatting.getContent(),
			chatting.getContentType(),
			chatting.getReadCount(),
			chatting.getSendDate());
	}

}
