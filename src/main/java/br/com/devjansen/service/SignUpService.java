package br.com.devjansen.service;

import br.com.devjansen.model.domain.UserDomain;
import br.com.devjansen.model.dto.request.CreateUserRequest;
import br.com.devjansen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    public String createNewUser(CreateUserRequest createUserRequest) {
        log.info("Creating new user...");
        UserDomain domain = UserDomain.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .createdDate(LocalDateTime.now())
                .build();
        return userRepository.save(domain).getId();
    }

}
