package com.watermelon.message.dto.chatRoom;

import jakarta.validation.constraints.NotBlank;

public record GetChatRoomRequest(
	@NotBlank(message = "userId는 필수값입니다.")
	String userId
) {
}
