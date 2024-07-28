package com.watermelon.message.presentation;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import com.watermelon.message.application.KafkaMessageService;
import com.watermelon.message.dto.chat.SendChatRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ChatController {
	private final KafkaMessageService kafkaMessageService;

	@MessageMapping("/room/{roomId}")
	public void sendMessage(@DestinationVariable String roomId, SendChatRequest message) {
		log.info("roomID = {}", roomId);
		kafkaMessageService.send("message", roomId, message);
	}
}