package com.watermelon.message.domain.mongo.chat;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
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

}
