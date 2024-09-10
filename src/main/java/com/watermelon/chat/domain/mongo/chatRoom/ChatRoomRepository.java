package com.watermelon.chat.domain.mongo.chatRoom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	@Query("{ 'chatUsers.users.userId': ?0 }")
	List<ChatRoom> findByUserId(String userId, Pageable pageable);
}
