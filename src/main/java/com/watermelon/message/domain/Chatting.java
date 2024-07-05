package com.watermelon.message.domain;


import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "chatting")
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chatting {

	@Id
	private String id;
	private Integer chatRoomNo;
	private String senderId;
	private String senderName;
	private String contentType;
	private String content;
	private long readCount;
	private LocalDateTime sendDate;
}
