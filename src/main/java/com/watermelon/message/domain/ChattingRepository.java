package com.watermelon.message.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChattingRepository extends MongoRepository<Chatting, String> {
}
