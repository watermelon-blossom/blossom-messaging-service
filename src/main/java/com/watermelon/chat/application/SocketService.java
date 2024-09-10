package com.watermelon.chat.application;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.watermelon.chat.domain.mongo.chat.ChatReadService;
import com.watermelon.chat.domain.mongo.chatRoom.ChatRoom;
import com.watermelon.chat.dto.chat.ChatResponse;
import com.watermelon.chat.dto.chat.SendChatRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomByLastMessageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.watermelon.chat.config.socketIO.SocketServerEvent.BROADCAST;

/*
 * 소켓 관련 비즈니스 로직 수행
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SocketService {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final ChatReadService chatReadService;

    public void sendNextToClient(GetChatRoomByLastMessageId request, AckRequest client) {
        client.sendAckData(chatService.getChatFromLastMessageId(request.roomId(), request.lastMessageId()));
    }

    /*
     * 메시지 송신자를 제외한 모든 클라이언트들에게 메시지 전송
     * room: 메시지를 보낼 room 이름
     * eventName: 소켓에게 트리거 할 event 이름
     * senderClient: 이벤트 송신자 정보
     * message: 소켓들에게 Broadcasting 할 메시지
     */
    public void broadcastToRoom(SocketIOClient senderClient, SendChatRequest sendChatRequest) {
        log.info("broadCasting To [{}]", sendChatRequest);
        String roomId = sendChatRequest.roomId();

        ChatResponse chatResponse = chatService.save(sendChatRequest);
        senderClient.getNamespace().getRoomOperations(roomId).sendEvent(BROADCAST.toString(), chatResponse);
    }

    public void onJoin(String roomId, AckRequest ackSender) {
        ChatRoom chatRoom = chatRoomService.getChatRoomEntityByRoomId(roomId);
        chatRoom.readRoomByUser(chatReadService, "joonhyeok");
        ackSender.sendAckData(chatService.getLatest20Chats(roomId));
    }
}