package br.com.devjansen.exception;

import br.com.devjansen.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {

    public abstract HttpStatus getStatus();

    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    // Document
    public String getResponseBodyCode() {
        return null;
    }

    // Document
    public ErrorResponse getResponseBody() {
        return null;
    }

}
