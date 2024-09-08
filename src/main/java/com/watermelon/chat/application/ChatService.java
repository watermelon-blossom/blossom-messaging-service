package com.watermelon.chat.application;

import static com.watermelon.chat.global.error.ErrorType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ChatRepository;
import com.watermelon.chat.dto.chat.ChatResponse;
import com.watermelon.chat.dto.chat.SendChatRequest;
import com.watermelon.chat.global.error.ApplicationException;

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

	public List<ChatResponse> getChatFromLastMessageId(String roomId, String lastMessageId) {
		Chat chat = chatRepository.findById(lastMessageId).orElseThrow(() -> new ApplicationException(NO_SUCH_CHAT));
		LocalDateTime lastMessageSendTime = chat.getSendDate();
		return chatRepository.findTop20ByRoomIdAndSendDateBeforeOrderBySendDateDesc(roomId, lastMessageSendTime);
	}

	public List<ChatResponse> getLatest20Chats(String roomId) {
		return chatRepository.findTop20ByRoomIdOrderBySendDateDesc(roomId);
	}

	@Transactional
	public ChatResponse save(SendChatRequest request) {
		//save Chatting to repository
		Chat chat = request.toEntity();
		Chat saved = chatRepository.save(chat);
		return ChatResponse.from(saved);
	}
}
