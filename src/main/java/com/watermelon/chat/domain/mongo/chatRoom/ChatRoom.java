package com.watermelon.chat.domain.mongo.chatRoom;

import static com.watermelon.chat.domain.mongo.chatRoom.RoomStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.dto.chat.ChatResponse;
import com.watermelon.chat.global.common.BaseEntity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "chatRooms")
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoom extends BaseEntity {
	@Id
	private String id;
	private ChatUsers chatUsers;
	private RoomStatus roomStatus;

	@DBRef
	private List<Chat> chats = new ArrayList<>();

	public static ChatRoom createChatRoom(ChatUsers users) {
		return ChatRoom.builder()
			.chatUsers(users)
			.roomStatus(ACTIVE)
			.build();
	}

	void addUser(ChatUser user) {
		this.chatUsers.addUser(user);
	}

	public void deleteRoom() {
		this.chatUsers = null;
		this.roomStatus = RoomStatus.DELETED;
	}

	public boolean isUserInRoom(String userId) {
		List<ChatUser> users = chatUsers.getUsers();
		return users.stream()
			.map(ChatUser::getUserId)
			.anyMatch(id -> id.equals(userId));
	}

	public Chat getLatestChat() {
		return this.chats.get(0);
	}

	public List<ChatResponse> get20LatestChat() {
		return this.chats.stream()
			.map(ChatResponse::from)
			.limit(20)
			.toList();
	}

}
