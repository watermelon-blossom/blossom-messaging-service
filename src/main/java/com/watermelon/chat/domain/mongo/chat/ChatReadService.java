package com.watermelon.chat.domain.mongo.chat;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatReadService {
	private final MongoTemplate mongoTemplate;

	public void readRoom(String roomId, String userId) {
		// 조건을 정의: roomId가 일치하고, senderId가 userId와 다르며, hasRead가 false인 문서
		Query query = new Query();
		query.addCriteria(Criteria.where("roomId").is(roomId)
			.and("senderId").ne(userId)
			.and("hasRead").is(false));

		// 업데이트할 내용 정의: hasRead를 true로 설정
		Update update = new Update();
		update.set("hasRead", true);

		// 조건에 맞는 문서들을 한 번에 업데이트
		mongoTemplate.updateMulti(query, update, Chat.class);
	}

	public long getUnReadInRoomWithRoomIdAndUserId(String roomId, String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("roomId").is(roomId)
				.and("senderId").ne(userId)
				.and("hasRead").is(false));

		return mongoTemplate.count(query, Chat.class);
	}

	public long readByChatIds(List<String> chatIds) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").in(chatIds));

		// 업데이트할 내용 정의: hasRead를 true로 설정
		Update update = new Update();
		update.set("hasRead", true);

		// 조건에 맞는 문서들을 한 번에 업데이트
		UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Chat.class);
		return updateResult.getMatchedCount();
	}
}
