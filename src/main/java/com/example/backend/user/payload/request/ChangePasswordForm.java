package com.example.backend.user.payload.request;

import lombok.Data;

@Data
public class ChangePasswordForm {
    private String password;
    private String newPassword;
}
