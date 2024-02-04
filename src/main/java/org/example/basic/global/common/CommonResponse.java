package org.example.basic.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    private STATUS status;
    private T data;

    public enum STATUS {
        SUCCESS, FAIL
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(STATUS.SUCCESS, data);
    }

    public static CommonResponse<Void> createSuccessWithNoContent() {
        return new CommonResponse<>(STATUS.SUCCESS, null);
    }

    public static <T> CommonResponse<T> fail(T data) {
        return new CommonResponse<>(STATUS.FAIL, data);
    }
}
