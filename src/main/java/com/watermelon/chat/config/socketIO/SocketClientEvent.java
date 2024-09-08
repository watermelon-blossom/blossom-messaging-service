package com.watermelon.chat.config.socketIO;

public enum SocketClientEvent {
	SEND("send"),
	NEXT("next");
	private final String value;

	SocketClientEvent(String eventName) {
		this.value = eventName;
	}

	@Override
	public String toString() {
		return value;
	}
}
