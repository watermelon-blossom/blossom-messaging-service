package com.watermelon.chat.presentation;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.watermelon.chat.application.SocketService;
import com.watermelon.chat.dto.chat.SendChatRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomByLastMessageId;
import com.watermelon.chat.dto.chatRoom.JoinChatRoomRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.watermelon.chat.config.socketIO.SocketClientEvent.*;

@Slf4j
@Component
public class ChatSocketModule {

	private final SocketIOServer server;
	private final SocketService socketService;

	public ChatSocketModule(SocketIOServer server, SocketService socketService) {
		this.server = server;
		this.socketService = socketService;

		server.addNamespace("/chat");
		log.info(server.getNamespace("/chat").getName());
		// /chat.addAuthTokenListener();
		server.addConnectListener(onConnected());

		// 소켓 서버에서 연결 해제 시 콜백 지정
		server.addDisconnectListener(onDisconnected());
		server.getNamespace("/chat").addEventListener(JOIN.toString(), JoinChatRoomRequest.class, onJoinReceived());
		server.getNamespace("/chat").addEventListener(SEND.toString(), SendChatRequest.class, onChatReceived());
		server.getNamespace("/chat")
			.addEventListener(NEXT.toString(), GetChatRoomByLastMessageId.class, onNextReceived());

	}

	// 채팅방 입장 요청 때 실행
	private DataListener<JoinChatRoomRequest> onJoinReceived() {
		return (senderClient, data, ackSender) -> {
			log.info("roomId = " + data.roomId());
			senderClient.joinRoom(data.roomId());
			socketService.onJoin(data.roomId(), ackSender);
		};
	}

	// 채팅 로드 요청 때 실행
	private DataListener<GetChatRoomByLastMessageId> onNextReceived() {
		return (senderClient, data, ackSender) -> {
			socketService.sendNextToClient(data, ackSender);
		};
	}

	// 채팅을 받았을 때 실행
	private DataListener<SendChatRequest> onChatReceived() {
		return (senderClient, data, ackSender) -> {
			log.info(senderClient.getAllRooms().toString());
			log.info(senderClient.getNamespace().toString());
			System.out.println(senderClient.getAllRooms().toString());
			System.out.println(senderClient.getNamespace().getName());
			socketService.broadcastToRoom(senderClient, data);
		};
	}

	// 소켓 서버에 연결 시 실행
	private ConnectListener onConnected() {
		return (client) -> {
			log.info("Client[{}] - Connected from socket", client.getSessionId().toString());
		};
	}

	// 소켓 서버에 연결 해제 시 실행
	private DisconnectListener onDisconnected() {
		return client -> {
			log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
		};
	}

}