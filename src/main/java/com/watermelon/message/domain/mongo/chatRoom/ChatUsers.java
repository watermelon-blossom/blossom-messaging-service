package com.watermelon.message.domain.mongo.chatRoom;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatUsers {
	private List<ChatUser> users = new ArrayList<>();

	public static ChatUsers createChatUsers(
		List<String> userIds
	) {
		ChatUsers chatUsers = new ChatUsers();
		userIds.forEach(userId -> chatUsers.addUser(ChatUser.createChatUser(userId)));
		return chatUsers;
	}

	public void addUser(ChatUser user) {
		this.users.add(user);
	}
}