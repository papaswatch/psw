package com.papaswatch.psw.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Data
public class Response <T> {

    private final int code;
    private final String status;
    private T data;

    public static final int CODE_OK_VAL = HttpStatus.OK.value();
    public static final String CODE_OK_MSG = HttpStatus.OK.name();
    public static final int CODE_UNAUTHORIZED_VAL = HttpStatus.UNAUTHORIZED.value();
    public static final String CODE_UNAUTHORIZED_MSG = HttpStatus.UNAUTHORIZED.name();

    // 2xx 응답에 대해 data 필드가 포함된 response 를 만들 때 사용한다.
    public Response(int code, String status, T data) {
        this(code, status);
        this.data = data;
    }

    public static <T> Response<T> justOk() {
        return new Response<>(CODE_OK_VAL, CODE_OK_MSG);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(CODE_OK_VAL, CODE_OK_MSG, data);
    }

    public static <T> Response<T> unauthorized() {
        return new Response<>(CODE_UNAUTHORIZED_VAL, CODE_UNAUTHORIZED_MSG);
    }
}
