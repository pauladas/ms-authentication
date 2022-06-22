package br.com.devjansen.exception;


import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private Object responseBody;

    public RestException(String message) {
        super(message);
    }


}
