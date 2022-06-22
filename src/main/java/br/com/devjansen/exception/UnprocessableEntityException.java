package br.com.devjansen.exception;

import br.com.devjansen.entity.dto.response.ErrorResponse;
import lombok.Getter;

@Getter
public class UnprocessableEntityException extends RestException {

    private ErrorResponse responseBody;

    public UnprocessableEntityException(String code) {
        super(code);
        this.responseBody = ErrorResponse.builder()
                .code(code)
                .build();
    }

    public UnprocessableEntityException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.responseBody = errorResponse;
    }
}
