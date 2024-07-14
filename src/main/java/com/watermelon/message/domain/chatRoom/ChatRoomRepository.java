package com.watermelon.message.domain.chatRoom;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	List<ChatRoom> findByUserId(String userId, Pageable pageable);
}
