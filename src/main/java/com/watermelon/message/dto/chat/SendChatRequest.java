package com.watermelon.message.dto.chat;

import java.time.LocalDateTime;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ContentType;

public record SendChatRequest(
	String senderId,
	ContentType contentType,
	String content
){
	// SendChatRequest로부터 Chatting 객체를 생성하는 메서드
	public Chat toEntity(String roomId) {
		return Chat.builder()
			.roomId(roomId)
			.senderId(senderId())
			.contentType(contentType())
			.content(content())
			.hasRead(false)
			.sendDate(LocalDateTime.now())
			.build();
	}

}
