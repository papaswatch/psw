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

    public static final int OK_CODE = HttpStatus.OK.value();
    public static final String OK_STATUS = HttpStatus.OK.name();

    public static final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();
    public static final String BAD_REQUEST_STATUS = HttpStatus.BAD_REQUEST.name();

    public static final int UNAUTHORIZED_CODE = HttpStatus.UNAUTHORIZED.value();
    public static final String UNAUTHORIZED_STATUS = HttpStatus.UNAUTHORIZED.name();

    public Response(int code, String status, T data) {
        this(code, status);
        this.data = data;
    }

    public static Response<Void> of(int code, String status) {
        return new Response<>(code, status);
    }

    public static Response<Void> justOk() {
        return new Response<>(OK_CODE, OK_STATUS);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(OK_CODE, OK_STATUS, data);
    }

    public static Response<Void> badRequest() {
        return new Response<>(BAD_REQUEST_CODE, BAD_REQUEST_STATUS);
    }

    public static Response<Void> unauthorized() {
        return new Response<>(UNAUTHORIZED_CODE, UNAUTHORIZED_STATUS);
    }

}
