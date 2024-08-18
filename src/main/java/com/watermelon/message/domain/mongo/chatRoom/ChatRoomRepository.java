package com.watermelon.message.domain.mongo.chatRoom;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	@Query("{ 'chatUsers.users.userId': ?0 }")
	List<ChatRoom> findByUserId(String userId, Pageable pageable);

}
