package com.watermelon.message.global.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
	private final ErrorType errorType;
	private final Object data;

	public ApplicationException(ErrorType errorType, Object data, Throwable cause) {
		super(errorType.getMessage(), cause);
		this.errorType = errorType;
		this.data = data;
	}

	public ApplicationException(ErrorType errorType) {
		this(errorType, errorType.getMessage(), null);
	}
}