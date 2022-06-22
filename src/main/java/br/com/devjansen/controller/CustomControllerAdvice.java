package br.com.devjansen.controller;

import br.com.devjansen.entity.dto.response.ErrorResponse;
import br.com.devjansen.exception.RestException;
import br.com.devjansen.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Void> handleHttpRequestMethodNotSupported(Exception ex) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({RestException.class})
    public ResponseEntity<ErrorResponse> handleRestException(RestException ex) {
        log.error("{}", ex.getMessage(), ex);
        if (ex instanceof UnprocessableEntityException unprocessableEntityException) {
            return new ResponseEntity<>(unprocessableEntityException.getResponseBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

}
