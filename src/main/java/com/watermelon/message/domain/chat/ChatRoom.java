package com.watermelon.message.domain.chat;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "chatRoom")
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoom {
}
