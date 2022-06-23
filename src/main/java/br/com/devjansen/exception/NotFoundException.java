package br.com.devjansen.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class NotFoundException extends RestException {

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}

}
