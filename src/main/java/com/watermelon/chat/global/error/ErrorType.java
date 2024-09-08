package com.watermelon.chat.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
	BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),
	INVALID_REQUEST_PARAMETER(400, "INVALID_REQUEST_PARAMETER", "잘못된 요청 파라미터 입니다."),
	UNAUTHENTICATED(401, "UNAUTHENTICATED", "인증되지 않은 사용자입니다."),
	UNAUTHORIZED(403, "UNAUTHORIZED", "권한이 없는 사용자입니다."),

	USER_NOT_FOUND(404, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),

	METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", "지원하지 않는 Http Method 입니다."),

	INTERNAL_PROCESSING_ERROR(500, "INTERNAL_PROCESSING_ERROR", "내부 시스템 에러가 발생했습니다."),

	// chat room
	NOT_ENOUGH_USERS_TO_CREATE_CHAT_ROOM(400, "NOT_ENOUGH_USERS_TO_CREATE_CHAT_ROOM", "채팅방 생성시 이용자는 2명이어야 합니다."),
	NOT_VALID_USERS_TO_CREATE_CHAT_ROOM(400, "NOT_VALID_USER_TO_CREATE_CHAT_ROOM", "채팅방 생성시 이용자 아이디가 없을 수 없습니다."),
	NO_SUCH_CHATROOM(404, "NO_SUCH_CHATROOM", "채팅방을 찾을 수 없습니다."),

	// chat
	NO_SUCH_CHAT(404, "NO_SUCH_CHAT", "채팅을 찾을 수 없습니다."),

	// STOMP
	NOT_VALID_USER_TO_ENTER_CHAT_ROOM(403, "NOT_VALID_USER_TO_ENTER_CHAT_ROOM", "잘못된 채팅방 접근입니다."),
	MESSAGE_WITH_NO_DESTINATION(412, "MESSAGE_WITH_NO_DESTINATION", "헤더에 destination 값이 없습니다"),
	MESSAGE_WITH_NO_USERID_HEADER(412, "MESSAGE_WITH_NO_USERID_HEADER", "헤더에 userId 값이 없습니다"),
	MESSAGE_WITH_NO_TYPE_HEADER(412, "MESSAGE_WITH_NO_TYPE_HEADER", "헤더에 type 값이 없습니다"),
	MESSAGE_WITH_WRONG_TYPE_HEADER(412, "MESSAGE_WITH_WRONG_TYPE_HEADER", "헤더에 type값이 사전에 정의되지 않았습니다."),
	;

	private final int status;
	private final String code;
	private final String message;

}