package com.watermelon.message.domain.chatRoom;

import org.springframework.data.mongodb.core.mapping.Document;

import com.watermelon.message.global.common.BaseEntity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "chatRoom")
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

	public static ChatRoom createChatRoom(ChatUsers user) {
		return ChatRoom.builder()
			.chatUsers(ChatUsers.builder().build())
			.roomStatus(RoomStatus.ACTIVE)
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
