package com.watermelon.message.domain.chat;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "chat")
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chat {

	@Id
	private String id;
	private Long roomId;
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
