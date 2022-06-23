package br.com.devjansen.exception;

import br.com.devjansen.entity.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RestException {

	private String responseBodyCode;

	private ErrorResponse responseBody;

	public UnprocessableEntityException(String responseBodyCode) {
		this.responseBodyCode = responseBodyCode;
	}

	public UnprocessableEntityException(ErrorResponse responseBody) {
		this.responseBody = responseBody;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}

	@Override
	public String getResponseBodyCode() {
		return this.responseBodyCode;
	}

	@Override
	public ErrorResponse getResponseBody() {
		return this.responseBody;
	}

}
