package com.watermelon.chat.presentation;

import org.springframework.stereotype.Controller;

import com.watermelon.chat.application.KafkaMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebSocketChatController {
	private final KafkaMessageService kafkaMessageService;

	// @MessageMapping("/room/{roomId}")
	// public void sendMessage(
	// 	@DestinationVariable String roomId,
	// 	@Header("simpSessionId") String sessionId,
	// 	@Header("nativeHeaders") Map<String, List<String>> nativeHeaders,
	// 	SendChatRequest message) {
	//
	// 	log.info("roomID = {}, Session ID = {}", roomId, sessionId);
	// 	log.info("STOMP Headers = {}", nativeHeaders);
	//
	// 	kafkaMessageService.send("message", roomId, message);
	// }
	//
	// @MessageMapping("/room/{roomId}/user/{userId}")
	// public void getMessage(
	// 	@DestinationVariable String roomId,
	// 	@DestinationVariable String userId,
	// 	SendChatRequest message) {
	// 	log.info("roomID = {}, userId = {}", roomId, userId);
	// 	kafkaMessageService.send("message", roomId, message);
	// }
}