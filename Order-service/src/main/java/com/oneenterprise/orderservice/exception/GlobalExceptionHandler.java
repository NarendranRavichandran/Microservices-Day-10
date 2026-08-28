package com.oneenterprise.orderservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            OrderNotFoundException.class
    )
    public ResponseEntity<ApiError>
    handleOrderNotFound(
            OrderNotFoundException exception,
            WebRequest request) {

        return buildResponse(
                404,
                "ORDER_NOT_FOUND",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(
            InvalidOrderException.class
    )
    public ResponseEntity<ApiError>
    handleInvalidOrder(
            InvalidOrderException exception,
            WebRequest request) {

        return buildResponse(
                400,
                "INVALID_ORDER",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(
            DuplicateOrderException.class
    )
    public ResponseEntity<ApiError>
    handleDuplicateOrder(
            DuplicateOrderException exception,
            WebRequest request) {

        return buildResponse(
                409,
                "DUPLICATE_ORDER",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiError>
    handleValidation(
            MethodArgumentNotValidException exception,
            WebRequest request) {

        String message =
                exception
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .findFirst()
                        .map(error ->
                                error.getDefaultMessage())
                        .orElse(
                                "Invalid request"
                        );

        return buildResponse(
                400,
                "VALIDATION_ERROR",
                message,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>
    handleGeneral(
            Exception exception,
            WebRequest request) {

        return buildResponse(
                500,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                request
        );
    }

    private ResponseEntity<ApiError>
    buildResponse(
            int status,
            String error,
            String message,
            WebRequest request) {

        ApiError apiError =
                new ApiError();

        apiError.setTimestamp(
                LocalDateTime.now()
        );

        apiError.setStatus(status);

        apiError.setError(error);

        apiError.setMessage(message);

        apiError.setPath(
                request.getDescription(false)
        );

        apiError.setTraceId(
                null
        );

        return ResponseEntity
                .status(
                        HttpStatus.valueOf(status)
                )
                .body(apiError);
    }
}