package com.watermelon.chat.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watermelon.chat.domain.mongo.chatRoom.ChatRoom;
import com.watermelon.chat.domain.mongo.chatRoom.ChatRoomRepository;
import com.watermelon.chat.domain.mongo.chatRoom.ChatUsers;
import com.watermelon.chat.dto.chatRoom.ChatRoomResponse;
import com.watermelon.chat.dto.chatRoom.CreateChatRoomRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomRequest;
import com.watermelon.chat.global.error.ApplicationException;
import com.watermelon.chat.global.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public List<ChatRoomResponse> getChatRoomByUserId(GetChatRoomRequest request, Pageable pageable) {

		//TODO check if users in request is valid

		List<ChatRoom> chatRooms = chatRoomRepository.findByUserId(request.userId(), pageable);
		List<ChatRoomResponse> chatRoomResponses =
			chatRooms.stream()
				.map(chatRoom -> ChatRoomResponse.from(chatRoom, 0)
				).toList();

		return chatRoomResponses;
	}

	public ChatRoomResponse getChatRoomByRoomId(String roomId) {
		ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() ->
			new ApplicationException(ErrorType.NO_SUCH_CHATROOM));
		return ChatRoomResponse.from(room, 0);
	}

	public ChatRoom getChatRoomEntityByRoomId(String roomId) {
		return chatRoomRepository.findById(roomId).orElseThrow(() ->
			new ApplicationException(ErrorType.NO_SUCH_CHATROOM));
	}


	public String createChatRoom(CreateChatRoomRequest request) {
		//TODO check if users in request is valid
		ChatUsers chatUsers = ChatUsers.createChatUsers(request.userIds());
		ChatRoom chatRoom = ChatRoom.createChatRoom(chatUsers);

		ChatRoom room = chatRoomRepository.save(chatRoom);
		return room.getId();
	}

	public void deleteChatRoom(String roomId) {
		ChatRoom chatRoom = chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new ApplicationException(ErrorType.NO_SUCH_CHATROOM));

		chatRoom.deleteRoom();
	}

	public boolean isUserInRoom(String roomId, String userId) {
		Optional<ChatRoom> room = chatRoomRepository.findById(roomId);
		return room.map(chatRoom -> chatRoom.isUserInRoom(userId)).orElse(false);
	}
}
