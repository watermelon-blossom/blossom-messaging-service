package com.watermelon.message.application;

import org.springframework.transaction.annotation.Transactional;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ChatRepository;
import com.watermelon.message.dto.chatting.SendChatRequest;
import com.watermelon.message.dto.chatting.SendChatResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chattingRepository;

	@Transactional
	public SendChatResponse save(SendChatRequest request) {
		//save Chatting to repository
		Chat chatting = request.toEntity();
		Chat saved = chattingRepository.save(chatting);
		return SendChatResponse.from(saved);
	}
}
