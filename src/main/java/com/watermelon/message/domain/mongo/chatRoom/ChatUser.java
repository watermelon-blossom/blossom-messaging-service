package com.watermelon.message.domain.mongo.chatRoom;

import static lombok.AccessLevel.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ChatUser {
	private String userId;

	public static ChatUser createChatUser(String userId) {
		return ChatUser.builder()
			.userId(userId)
			.build();
	}
}
