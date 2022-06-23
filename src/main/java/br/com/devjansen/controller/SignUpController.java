package br.com.devjansen.controller;

import br.com.devjansen.model.dto.request.CreateUserRequest;
import br.com.devjansen.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sign-up")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public void signUp(@Valid @RequestBody CreateUserRequest createUserRequest,
                       HttpServletRequest request, HttpServletResponse response) {
        String newUserId = signUpService.createNewUser(createUserRequest);
        String location = StringUtils.join(request.getRequestURL(), "/", newUserId);
        response.addHeader("location", location);
    }

}
