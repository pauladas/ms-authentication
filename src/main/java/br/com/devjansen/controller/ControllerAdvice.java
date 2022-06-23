package br.com.devjansen.controller;

import br.com.devjansen.exception.NotFoundException;
import br.com.devjansen.exception.RestException;
import br.com.devjansen.model.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public List<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Collections.singletonList(ErrorResponse
                .builder()
                .code("400.001")
                .message(getMessage("400.001", e.getName()))
                .build());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public List<ErrorResponse> handleMessageNotReadableAndMissingServletException(Exception e) {
        ErrorResponse errorResponse;
        if (e.getCause() instanceof JsonMappingException jsonMappingException) {
            String field = jsonMappingException
                    .getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("."));
            errorResponse = ErrorResponse
                    .builder()
                    .code("400.001")
                    .message(getMessage("400.001", field))
                    .build();
        } else {
            errorResponse = ErrorResponse
                    .builder()
                    .code("400.000")
                    .message(getMessage("400.000"))
                    .build();
        }
        return Collections.singletonList(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Optional.ofNullable(e)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(BindingResult::getAllErrors)
                .orElse(new ArrayList<>())
                .stream()
                .filter(ObjectUtils::isNotEmpty)
                .map(objectError -> {
                    String field = "";
                    if (objectError instanceof FieldError fieldError) {
                        field = fieldError.getField();
                    }
                    return ErrorResponse.builder()
                            .code(objectError.getDefaultMessage())
                            .message(getMessage(objectError.getDefaultMessage(), field))
                            .build();
                }).toList();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public void handleNotFoundException(NotFoundException e) {

    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({RestException.class})
    public ErrorResponse handleRestException(RestException e) {
        return Optional.ofNullable(e.getResponseBody())
                .map(exceptionResponse ->
                        ErrorResponse.builder()
                                .code(exceptionResponse.getCode())
                                .message(Optional.ofNullable(exceptionResponse.getMessage())
                                        .orElse(getMessage(exceptionResponse.getCode())))
                                .build()
                )
                .orElse(ErrorResponse.builder()
                        .code(e.getResponseBodyCode())
                        .message(getMessage(e.getResponseBodyCode()))
                        .build());

    }

    private String getMessage(String code,
                              Object... args) {
        return this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
