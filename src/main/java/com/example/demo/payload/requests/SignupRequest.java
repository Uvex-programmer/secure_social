package com.example.demo.payload.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @NotBlank
    @Size(min = 8, max = 40)
    private String password;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
}
