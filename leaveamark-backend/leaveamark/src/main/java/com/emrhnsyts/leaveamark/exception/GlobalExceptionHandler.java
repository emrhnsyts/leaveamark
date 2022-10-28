package com.emrhnsyts.leaveamark.exception;

import com.emrhnsyts.leaveamark.exception.entity.ErrorResponse;
import com.emrhnsyts.leaveamark.exception.entity.ValidationErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        return exceptionUtil(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception exception) {
        return exceptionUtil(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception exception) {
        return exceptionUtil(exception.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(Exception exception) {
        return exceptionUtil(exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Validation failed");
        validationErrorResponse.setCreatedAt(new Date());
        validationErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        validationErrorResponse.setDetails(ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            return objectError.getDefaultMessage();
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(validationErrorResponse, validationErrorResponse.getHttpStatus());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException() {
        return exceptionUtil("Token is not matched.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException() {
        return exceptionUtil("Token is malformed.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException() {
        return exceptionUtil("Token is expired.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<Object> handleUnsupportedJwtException() {
        return exceptionUtil("Token is not supported.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return exceptionUtil("Token is not matched");
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(Exception exception) {
        return exceptionUtil("Your username and email must be unique.");
    }

    private ResponseEntity<Object> exceptionUtil(String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .createdAt(new Date()).build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
