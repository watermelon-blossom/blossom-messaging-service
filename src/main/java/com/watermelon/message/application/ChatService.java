package com.watermelon.message.application;

import static com.watermelon.message.global.error.ErrorType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.message.domain.chat.Chat;
import com.watermelon.message.domain.chat.ChatRepository;
import com.watermelon.message.dto.chat.ChatResponse;
import com.watermelon.message.dto.chat.SendChatRequest;
import com.watermelon.message.global.error.ApplicationException;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {

	private final ChatRepository chatRepository;

	public ChatResponse getChat(String chatId) {
		Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ApplicationException(NO_SUCH_CHAT));
		return ChatResponse.from(chat);
	}

	@Transactional
	public ChatResponse save(String roomId, SendChatRequest request) {
		//save Chatting to repository
		Chat chat = request.toEntity(roomId);
		Chat saved = chatRepository.save(chat);
		return ChatResponse.from(saved);
	}
}
