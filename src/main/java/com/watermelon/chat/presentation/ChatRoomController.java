package com.watermelon.chat.presentation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.chat.application.ChatRoomService;
import com.watermelon.chat.dto.chatRoom.ChatRoomResponse;
import com.watermelon.chat.dto.chatRoom.CreateChatRoomRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomRequest;
import com.watermelon.chat.global.support.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@GetMapping("/chatRooms")
	public ApiResponse<List<ChatRoomResponse>> getChatRoom(
		@Valid GetChatRoomRequest request,
		Pageable pageable
	) {
		List<ChatRoomResponse> chatRooms = chatRoomService.getChatRoomByUserId(request, pageable);
		return ApiResponse.success(chatRooms);
	}

	@GetMapping("/chatRooms/{roomId}")
	public ApiResponse<ChatRoomResponse> getChatRoomByRoomId(
		@PathVariable String roomId
	) {
		ChatRoomResponse chatRoom = chatRoomService.getChatRoomByRoomId(roomId);
		return ApiResponse.success(chatRoom);
	}

	@PostMapping("/chatRooms")
	public ApiResponse<String> createChatRoom(
		@Valid @RequestBody CreateChatRoomRequest request
	) {
		String roomId = chatRoomService.createChatRoom(request);
		return ApiResponse.success(roomId);
	}

	@DeleteMapping("/chatRooms/{roomId}")
	public ApiResponse<String> deleteChatRooms(
		@PathVariable String roomId
	) {
		chatRoomService.deleteChatRoom(roomId);
		return ApiResponse.success("success");
	}
}
