package com.watermelon.chat.dto.chat;

import java.util.List;

public record ReadChatRequest(
        List<String> chatIds
) {
}
