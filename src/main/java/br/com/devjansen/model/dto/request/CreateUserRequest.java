package br.com.devjansen.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "400.001")
    private String name;

    @NotBlank(message = "400.001")
    private String email;

    @NotBlank(message = "400.001")
    private String password;

}
