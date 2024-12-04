package com.codeit.torip.common.exception;

import com.codeit.torip.common.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.codeit.torip.common.contant.ToripConstants.HttpConstant.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseDto expiredJwtExceptionHandler(ExpiredJwtException e) {
        return ResponseDto.fail(FORBIDDEN_CODE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto defaultExceptionHandler(Exception e) {
        log.error("Exception", e);
        return ResponseDto.fail(SERVER_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseDto dataAccessExceptionHandler(DataAccessException e) {
        log.error("DataAccessException", e);
        return ResponseDto.fail(CLIENT_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return ResponseDto.fail(CLIENT_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseDto constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        return ResponseDto.fail(CLIENT_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDto httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return ResponseDto.fail(CLIENT_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDto illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("lIllegalArgumentException", e);
        return ResponseDto.fail(CLIENT_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDto httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return ResponseDto.fail(METHOD_NOT_ALLOWED_CODE, e.getMessage());
    }

}
