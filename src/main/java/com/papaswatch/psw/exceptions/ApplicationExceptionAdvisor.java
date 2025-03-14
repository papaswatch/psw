package com.papaswatch.psw.exceptions;

import com.papaswatch.psw.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionAdvisor {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response<?>> applicationException(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.toResponse());
    }
}
