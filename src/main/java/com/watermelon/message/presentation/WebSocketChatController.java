package com.watermelon.message.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.watermelon.message.application.KafkaMessageService;
import com.watermelon.message.dto.chat.SendChatRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebSocketChatController {
	private final KafkaMessageService kafkaMessageService;

	@MessageMapping("/room/{roomId}")
	public void sendMessage(
		@DestinationVariable String roomId,
		@Header("simpSessionId") String sessionId,
		@Header("nativeHeaders") Map<String, List<String>> nativeHeaders,
		SendChatRequest message) {

		log.info("roomID = {}, Session ID = {}", roomId, sessionId);
		log.info("STOMP Headers = {}", nativeHeaders);

		kafkaMessageService.send("message", roomId, message);
	}

	@MessageMapping("/room/{roomId}/user/{userId}")
	public void getMessage(
		@DestinationVariable String roomId,
		@DestinationVariable String userId,
		SendChatRequest message) {
		log.info("roomID = {}, userId = {}", roomId, userId);
		kafkaMessageService.send("message", roomId, message);
	}
}