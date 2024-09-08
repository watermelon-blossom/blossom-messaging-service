package com.watermelon.chat.dto.chat;

import static com.watermelon.chat.constant.Constant.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ContentType;

public record ChatResponse(
	String roomId,
	String senderId,
	String content,
	ContentType contentType,
	boolean hasRead,
	@JsonFormat(pattern = DEFAULT_REQUEST_PARAM_TIME_FORMAT) @JsonSerialize(using = LocalDateTimeSerializer.class)
	LocalDateTime sendDate
) {

	public static ChatResponse from(Chat chat) {
		return new ChatResponse(
			chat.getRoomId(),
			chat.getSenderId(),
			chat.getContent(),
			chat.getContentType(),
			chat.isHasRead(),
			chat.getSendDate());
	}

}
