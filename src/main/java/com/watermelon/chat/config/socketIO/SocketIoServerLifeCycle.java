package com.watermelon.chat.config.socketIO;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketIoServerLifeCycle {

	private final SocketIOServer server;
	private static final int RETRY_INTERVAL_MS = 5000; // 5초 대기 간격
	private static final int MAX_RETRIES = 12; // 최대 12번 시도 (5초 * 12 = 1분)

	@Value("${socket-server.port}")
	private int port;

	@PostConstruct
	public void start() {
		int retryCount = 0;
		while (!isPortAvailable(port) && retryCount < MAX_RETRIES) {
			log.warn("Port {} is already in use. Retrying in {} milliseconds... (Attempt {}/{})",
				port, RETRY_INTERVAL_MS, retryCount + 1, MAX_RETRIES);

			try {
				Thread.sleep(RETRY_INTERVAL_MS);
			} catch (InterruptedException e) {
				log.error("Thread was interrupted during port availability wait", e);
				Thread.currentThread().interrupt(); // 인터럽트 상태 복원
			}
			retryCount++;
		}

		if (isPortAvailable(port)) {
			log.info("Port {} is now available. Starting SocketIOServer...", port);
			server.start();
		} else {
			log.error("Port {} is still in use after {} attempts. SocketIOServer cannot be started.", port,
				MAX_RETRIES);
		}
	}

	/**
	 * SocketIo 서버 종료
	 */
	@PreDestroy
	public void stop() {
		log.info("Stopping SocketIOServer...");
		server.stop();
	}

	/**
	 * 포트가 사용 중인지 확인
	 */
	private boolean isPortAvailable(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			serverSocket.setReuseAddress(true);
			return true;
		} catch (BindException e) {
			// 포트가 이미 사용 중인 경우
			return false;
		} catch (IOException e) {
			log.error("Error while checking port availability: {}", e.getMessage());
			return false;
		}
	}
}
