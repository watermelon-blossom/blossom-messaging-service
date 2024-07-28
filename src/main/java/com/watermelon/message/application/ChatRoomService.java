package com.watermelon.message.application;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watermelon.message.domain.chatRoom.ChatRoom;
import com.watermelon.message.domain.chatRoom.ChatRoomRepository;
import com.watermelon.message.domain.chatRoom.ChatUsers;
import com.watermelon.message.dto.chatRoom.ChatRoomResponse;
import com.watermelon.message.dto.chatRoom.CreateChatRoomRequest;
import com.watermelon.message.dto.chatRoom.GetChatRoomRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public ChatRoomResponse getChatRoomByUserId(GetChatRoomRequest request, Pageable pageable) {

		//TODO check if users in request is valid

		List<ChatRoom> chatRooms = chatRoomRepository.findByUserId(request.userId(), pageable);

		return null;
	}

	public String createChatRoom(CreateChatRoomRequest request) {
		//TODO check if users in request is valid
		ChatUsers chatUsers = ChatUsers.createChatUsers(request.userIds());
		ChatRoom chatRoom = ChatRoom.createChatRoom(chatUsers);

		ChatRoom room = chatRoomRepository.save(chatRoom);
		return room.getId();
	}
}
