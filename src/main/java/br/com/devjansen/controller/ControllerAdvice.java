package br.com.devjansen.controller;

import br.com.devjansen.entity.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
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

	private String getMessage(String code,
							  Object... args) {
		return this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

}