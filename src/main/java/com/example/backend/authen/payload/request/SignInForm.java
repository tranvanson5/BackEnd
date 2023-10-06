package com.example.backend.authen.payload.request;

import lombok.Data;

@Data
public class SignInForm {
    private String username;
    private String password;
}
