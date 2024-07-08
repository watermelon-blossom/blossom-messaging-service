package com.watermelon.message.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chat.Chat;

public record SendChatRequest(
	Integer roomId,
	String senderId,
	String contentType,
	String content
){
	// SnedMessageRequest로부터 Chatting 객체를 생성하는 메서드
	public Chat toEntity() {
		return Chat.builder()
				.roomId(roomId())
				.senderId(senderId())
				.contentType(contentType())
				.content(content())
				.readCount(0)
				.sendDate(LocalDateTime.now())
				.build();
	}

}
