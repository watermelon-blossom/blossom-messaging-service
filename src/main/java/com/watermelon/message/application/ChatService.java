package com.watermelon.message.application;

import org.springframework.transaction.annotation.Transactional;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ChatRepository;
import com.watermelon.message.dto.chat.SendChatRequest;
import com.watermelon.message.dto.chat.SendChatResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;

	@Transactional
	public SendChatResponse save(SendChatRequest request) {
		//save Chatting to repository
		Chat chat = request.toEntity();
		Chat saved = chatRepository.save(chat);
		return SendChatResponse.from(saved);
	}
}
