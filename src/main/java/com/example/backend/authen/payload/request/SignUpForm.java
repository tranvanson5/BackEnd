package com.example.backend.authen.payload.request;

import lombok.Data;

@Data
public class SignUpForm {
    private String name;
    private String email;
    private String username;
    private String password;
}
