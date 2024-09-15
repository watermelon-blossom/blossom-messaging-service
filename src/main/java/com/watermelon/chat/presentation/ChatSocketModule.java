package com.watermelon.chat.presentation;

import com.corundumstudio.socketio.AuthTokenListener;
import com.corundumstudio.socketio.AuthTokenResult;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.chat.application.SocketService;
import com.watermelon.chat.dto.chat.ReadChatRequest;
import com.watermelon.chat.dto.chat.SendChatRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomByLastChatId;
import com.watermelon.chat.dto.chatRoom.JoinChatRoomRequest;
import com.watermelon.chat.global.error.ApplicationException;
import com.watermelon.chat.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

import static com.watermelon.chat.config.socketIO.SocketClientEvent.*;
import static com.watermelon.chat.global.error.ErrorType.*;

@Slf4j
@Component
public class ChatSocketModule {

    private final SocketService socketService;
    private final JwtTokenProvider jwtUtils;

    public ChatSocketModule(SocketIOServer server, SocketService socketService, JwtTokenProvider jwtUtils) {
        this.socketService = socketService;
        this.jwtUtils = jwtUtils;

        SocketIONamespace chatServer = server.addNamespace("/chat");
        log.info(server.getNamespace("/chat").getName());
        // /chat.addAuthTokenListener();
        server.addConnectListener(onConnected());

        // 소켓 서버에서 연결 해제 시 콜백 지정
        server.addDisconnectListener(onDisconnected());
        chatServer.addAuthTokenListener(jwtAuthListener());
        chatServer.addEventListener(READ.toString(), ReadChatRequest.class, onReadChatReceived());
        chatServer.addEventListener(JOIN.toString(), JoinChatRoomRequest.class, onJoinReceived());
        chatServer.addEventListener(SEND.toString(), SendChatRequest.class, onChatReceived());
        chatServer.addEventListener(NEXT.toString(), GetChatRoomByLastChatId.class, onNextReceived());

    }

    private static void validateTokenExist(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty())
            throw new ApplicationException(CONNECT_WITHOUT_AUTH_TOKEN);

        if (!jwtToken.startsWith("Bearer ")) {
            throw new ApplicationException(INVALID_AUTH_TOKEN);
        }
    }

    private static String parseTokenObject(Object tokenObject) {
        if (tokenObject == null) {
            throw new ApplicationException(CONNECT_WITHOUT_AUTH_TOKEN);
        }
        log.info("token obejct [{}]", tokenObject);
        LinkedHashMap<String, String> linkedHashMap = new ObjectMapper().convertValue(tokenObject, LinkedHashMap.class);
        String jwtToken = linkedHashMap.get("token");
        return jwtToken;
    }

    private static String validateAndGetJwt(Object tokenObject) {
        String jwtToken = parseTokenObject(tokenObject);
        validateTokenExist(jwtToken);
        String jwt = jwtToken.split(" ")[1];
        return jwt;
    }

    private AuthTokenListener jwtAuthListener() {
        return (tokenObject, senderClient) -> {
            String jwt = validateAndGetJwt(tokenObject);
            boolean hasValidated = jwtUtils.validateToken(jwt);
            if (hasValidated) {
                return AuthTokenResult.AuthTokenResultSuccess;
            } else {
                return new AuthTokenResult(false, UNAUTHENTICATED);
            }
        };
    }

    // 채팅방 입장 요청 때 실행
    private DataListener<JoinChatRoomRequest> onJoinReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Join room request = " + data.roomId());
            senderClient.joinRoom(data.roomId());
            String jwt = validateAndGetJwt(senderClient.getHandshakeData().getAuthToken());
            socketService.onJoin(data.roomId(), jwtUtils.getUserIdFromJwt(jwt), ackSender);
        };
    }

    private DataListener<ReadChatRequest> onReadChatReceived() {
        return (senderClient, data, ackSender) -> {
            socketService.readChat(data, ackSender);
        };
    }

    // 채팅 로드 요청 때 실행
    private DataListener<GetChatRoomByLastChatId> onNextReceived() {
        return (senderClient, data, ackSender) -> {
            socketService.sendNextToClient(data, ackSender);
        };
    }

    // 채팅을 받았을 때 실행
    private DataListener<SendChatRequest> onChatReceived() {
        return (senderClient, data, ackSender) -> {
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