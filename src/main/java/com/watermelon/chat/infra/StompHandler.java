package com.watermelon.chat.infra;

import static com.watermelon.chat.global.error.ErrorType.*;

import java.security.Principal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.watermelon.chat.application.ChatRoomService;
import com.watermelon.chat.domain.mongo.chat.ChatReadService;
import com.watermelon.chat.global.error.ApplicationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

	private final ChatReadService chatReadService;
	private final ChatRoomService chatRoomService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		switch (accessor.getCommand()) {
			case SUBSCRIBE -> {
				String userId = accessor.getFirstNativeHeader("userId");

				if (userId == null) {
					throw new ApplicationException(MESSAGE_WITH_NO_USERID_HEADER);
				}

				String destination = accessor.getDestination();
				if (destination == null) {
					throw new ApplicationException(MESSAGE_WITH_NO_DESTINATION);
				}

				String[] destinationParts = destination.split("/");
				String roomId = destinationParts[destinationParts.length - 1]; // 마지막 문자열을 roomId로 저장

				if (!chatRoomService.isUserInRoom(roomId, userId))
					throw new ApplicationException(NOT_VALID_USER_TO_ENTER_CHAT_ROOM);

				// roomId를 이용한 추가 로직을 여기에서 구현
				chatReadService.readRoom(roomId, userId);
			}
			case CONNECT -> {
			}
		}
		return message;
	}

	@EventListener
	public void handleWebSocketConnectionListener(SessionConnectedEvent event) {
		Principal user = event.getUser();
		log.info("사용자 입장");
	}

	@EventListener
	public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
		log.info("사용자 퇴장");
	}
}