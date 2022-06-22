package br.com.devjansen.controller;

import br.com.devjansen.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/sign-up")
public class SignUpController {

    @PostMapping
    public void signUp() {
        log.info("Sign up called");
        throw new UnprocessableEntityException("123");
    }

}
