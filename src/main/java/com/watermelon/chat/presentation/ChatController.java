package com.watermelon.chat.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.chat.application.ChatService;
import com.watermelon.chat.dto.chat.ChatResponse;
import com.watermelon.chat.global.support.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@GetMapping("/chats/{chatId}")
	public ApiResponse<ChatResponse> getChatByChatId(
		@PathVariable String chatId
	) {
		ChatResponse chat = chatService.getChat(chatId);
		return ApiResponse.success(chat);
	}

}
