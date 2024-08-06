package com.watermelon.message.domain.mongo.chatRoom;

import static com.watermelon.message.domain.mongo.chatRoom.RoomStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.watermelon.message.domain.mongo.chat.Chat;
import com.watermelon.message.global.common.BaseEntity;

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
}
