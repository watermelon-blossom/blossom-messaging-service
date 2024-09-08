package com.watermelon.chat.dto.chatRoom;

import java.util.List;

import com.watermelon.chat.global.error.ApplicationException;
import com.watermelon.chat.global.error.ErrorType;

import jakarta.validation.constraints.NotEmpty;

public record CreateChatRoomRequest(
	@NotEmpty
	List<String> userIds
) {
	public CreateChatRoomRequest {
		if (userIds.size() != 2) {
			throw new ApplicationException(ErrorType.NOT_ENOUGH_USERS_TO_CREATE_CHAT_ROOM);
		}
		if (userIds.get(0).isBlank() || userIds.get(1).isBlank()) {
			throw new ApplicationException(ErrorType.NOT_VALID_USERS_TO_CREATE_CHAT_ROOM);
		}
	}
}
