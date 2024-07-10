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
	private Integer roomId;
	private String senderId;
	private String contentType;
	private String content;
	private long readCount;
	private LocalDateTime sendDate;

	public void readCountUp() {
		this.readCount++;
	}

}
