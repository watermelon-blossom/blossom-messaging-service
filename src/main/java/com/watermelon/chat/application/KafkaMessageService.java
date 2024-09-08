package com.watermelon.chat.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.watermelon.chat.dto.chat.ChatResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

	private final KafkaTemplate<String, ChatResponse> kafkaTemplate;
	private final ChatService chatService;
	private final SimpMessagingTemplate template;

	// //producer
	// public void send(String topic, String roomId, SendChatRequest messageDto) {
	// 	log.debug("send Message : " + messageDto);
	// 	ChatResponse responseMessageDto = chatService.save(roomId, messageDto);
	// 	kafkaTemplate.send(topic, responseMessageDto);
	// }
	//
	// //consumer
	// @KafkaListener(topics = "#{'${spring.kafka.topic.names}'}")
	// public void consume(@Payload ChatResponse responseMessageDto) {
	// 	template.convertAndSend("/room/" + responseMessageDto.roomId(), responseMessageDto);
	// }

}