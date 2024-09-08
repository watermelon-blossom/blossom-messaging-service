package com.watermelon.chat.dto.chatRoom;

import jakarta.validation.constraints.NotBlank;

public record JoinChatRoomRequest(
	@NotBlank(message = "roomId는 필수값입니다.")
	String roomId
) {
}
