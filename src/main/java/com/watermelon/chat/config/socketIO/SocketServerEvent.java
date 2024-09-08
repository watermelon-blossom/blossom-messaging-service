package com.watermelon.chat.config.socketIO;

public enum SocketServerEvent {
	BROADCAST("broadcast"),
	INIT("init");
	private final String value;

	SocketServerEvent(String eventName) {
		this.value = eventName;
	}

	@Override
	public String toString() {
		return value;
	}
}
