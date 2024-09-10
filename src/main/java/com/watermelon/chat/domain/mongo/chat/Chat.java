package com.watermelon.chat.domain.mongo.chat;

import com.watermelon.chat.application.ChatRoomService;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "chats")
public class Chat {

	@Id
	private String id;
	private String roomId;
	private String senderId;
	private ContentType contentType;
	private String content;
	private boolean hasRead;
	private LocalDateTime sendDate;
	private String url;

	public void checkRead() {
		hasRead = true;
	}

	public void syncWithChatRoom(ChatRoomService chatRoomService) {
		chatRoomService.saveChatIntoChatroom(this);
	}

}
