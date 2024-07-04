package com.watermelon.message.global.support;



import com.watermelon.message.global.error.ErrorMessage;
import com.watermelon.message.global.error.ErrorType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private ResponseType responseType;
    private T data;
    private ErrorMessage error;
    private String debug;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseType.SUCCESS, data, null, null);
    }

    public static <T> ApiResponse<T> error(ErrorType errorType, String errorData, String debug) {
        return new ApiResponse<>(
            ResponseType.ERROR, null, new ErrorMessage(errorType, errorData), debug);
    }
}