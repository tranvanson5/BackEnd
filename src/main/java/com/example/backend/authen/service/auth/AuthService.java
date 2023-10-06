package com.example.backend.authen.service.auth;

import com.example.backend.authen.payload.request.SignInForm;
import com.example.backend.authen.payload.request.SignUpForm;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> SignIn(SignInForm signInForm);

    ResponseEntity<?> SignUp(SignUpForm signUpForm);
}
