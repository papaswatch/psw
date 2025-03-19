package com.papaswatch.psw.exceptions;

import com.papaswatch.psw.common.dto.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public ApplicationException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static ApplicationException badRequest() {
        return new ApplicationException(HttpStatus.BAD_REQUEST);
    }

    public static ApplicationException notFound() {
        return new ApplicationException(HttpStatus.NOT_FOUND);
    }

    public Response<Void> toResponse() {
        return Response.of(httpStatus.value(), this.getMessage());
    }

    public static ApplicationException SellerNotFound() {
        return new ApplicationException("Seller not found");
    }

    public static ApplicationException UserNotFound() {
        return new ApplicationException("User not found");
    }

    public static ApplicationException CartDataNotFound() {
        return new ApplicationException("Cart data not found");
    }
}
