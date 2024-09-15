package com.watermelon.chat.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.watermelon.chat.domain.mongo.chat.Chat;
import com.watermelon.chat.domain.mongo.chat.ContentType;

import java.time.LocalDateTime;

import static com.watermelon.chat.constant.Constant.DEFAULT_REQUEST_PARAM_TIME_FORMAT;

public record ChatResponse(
        String chatId,
        String roomId,
        String senderId,
        String content,
        ContentType contentType,
        boolean hasReceiverRead,
        @JsonFormat(pattern = DEFAULT_REQUEST_PARAM_TIME_FORMAT) @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime sendDate
) {

    public static ChatResponse from(Chat chat) {
        return new ChatResponse(
                chat.getId(),
                chat.getRoomId(),
                chat.getSenderId(),
                chat.getContent(),
                chat.getContentType(),
                chat.isHasRead(),
                chat.getSendDate());
    }

}
