package com.example.backend.authen.payload.request;

import lombok.Data;

@Data
public class RestPassword {
    private String password;
    private String newPassword;
}
