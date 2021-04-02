package com.tesco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknownException(Exception e)  {
        return handleException(e, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(Exception e)  {
        return handleException(e, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(Exception e)  {
        return handleException(e, BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ApiError> handleServletRequestBindingExceptionException(Exception e)  {
        return handleException(e, BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(Exception e)  {
        return handleException(e, NOT_FOUND);
    }

    private ResponseEntity<ApiError> handleException(Exception e, HttpStatus httpStatus){
        ApiError apiError = buildApiError(e, httpStatus);
        return ResponseEntity.status(apiError.getStatus())
            .body(apiError);
    }

    private ApiError buildApiError(Exception e, HttpStatus httpStatus) {
        return ApiError.builder()
            .error(httpStatus.getReasonPhrase().toLowerCase().replace(" ", "_"))
            .message(e.getMessage())
            .status(httpStatus.value())
            .build();
    }
}
