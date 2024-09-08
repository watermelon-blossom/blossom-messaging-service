package com.watermelon.chat.domain.mongo.chat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.watermelon.chat.dto.chat.ChatResponse;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

	// createdAt이 특정 시간 이전인 항목을 20개 조회
	List<ChatResponse> findTop20ByRoomIdAndSendDateBeforeOrderBySendDateDesc(String roomId, LocalDateTime sendDate);

	List<ChatResponse> findTop20ByRoomIdOrderBySendDateDesc(String roomId);
}
