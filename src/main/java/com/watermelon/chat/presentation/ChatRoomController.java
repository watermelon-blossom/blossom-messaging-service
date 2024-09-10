package com.watermelon.chat.presentation;

import com.watermelon.chat.application.ChatRoomService;
import com.watermelon.chat.dto.chatRoom.ChatRoomResponse;
import com.watermelon.chat.dto.chatRoom.CreateChatRoomRequest;
import com.watermelon.chat.dto.chatRoom.GetChatRoomRequest;
import com.watermelon.chat.global.support.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
			@PathVariable("roomId") String roomId
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
			@PathVariable("roomId") String roomId
	) {
		chatRoomService.deleteChatRoom(roomId);
		return ApiResponse.success("success");
	}
}
