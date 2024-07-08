package com.watermelon.message.application;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.watermelon.message.dto.chatting.SendChatRequest;
import com.watermelon.message.dto.chatting.SendChatResponse;
import com.watermelon.message.global.error.ApplicationException;
import com.watermelon.message.global.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

    private final KafkaTemplate<String, SendChatResponse> kafkaTemplate;
    private final ChatService chatService;
    //producer
    public void send(String topic, SendChatRequest messageDto) {
        log.info("send Message : " + messageDto);
        try{
            SendChatResponse responseMessageDto = chatService.save(messageDto);
            kafkaTemplate.send(topic,responseMessageDto);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(ErrorType.INTERNAL_PROCESSING_ERROR);
        }
    }

    //consumer
    //TODO SimpleMessageOperation vs SimpMessagingTemplate 논의
    private final SimpMessageSendingOperations sendingOperations;
    private final SimpMessagingTemplate template;
    @KafkaListener(topics = "#{'${spring.kafka.topic.names}'}")
    public void consume(SendChatResponse responseMessageDto) throws IOException {
        template.convertAndSend("/room/"+ responseMessageDto.roomId(), responseMessageDto);
    }


}