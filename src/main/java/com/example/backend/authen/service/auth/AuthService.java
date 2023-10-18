package com.example.backend.authen.service.auth;

import com.example.backend.authen.payload.request.RestPassword;
import com.example.backend.authen.payload.request.SignInForm;
import com.example.backend.authen.payload.request.SignUpForm;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    ResponseEntity<?> SignIn(SignInForm signInForm);

    ResponseEntity<?> SignUp(SignUpForm signUpForm);

    ResponseEntity<?> generateOtpForgotPassword(String email);

    ResponseEntity<?> veritifyOtpForgotPassword(String email, String otp);

    ResponseEntity<?> resetPassword(RestPassword password, HttpServletRequest request);
}
