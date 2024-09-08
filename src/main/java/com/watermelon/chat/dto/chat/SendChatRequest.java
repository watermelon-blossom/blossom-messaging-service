package com.watermelon.chat.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ContentType;

public record SendChatRequest(
	String roomId,
	String senderId,
	ContentType contentType,
	String content
){
	// SendChatRequest로부터 Chatting 객체를 생성하는 메서드
	public Chat toEntity() {
		return Chat.builder()
			.roomId(roomId())
			.senderId(senderId())
			.contentType(contentType())
			.content(content())
			.hasRead(false)
			.sendDate(LocalDateTime.now())
			.build();
	}

}
