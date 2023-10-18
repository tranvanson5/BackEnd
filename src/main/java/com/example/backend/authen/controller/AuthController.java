package com.example.backend.authen.controller;

import com.example.backend.authen.payload.request.RestPassword;
import com.example.backend.authen.payload.request.SignInForm;
import com.example.backend.authen.payload.request.SignUpForm;
import com.example.backend.authen.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInForm signInForm) {
        return authService.SignIn(signInForm);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> authenticateSignUpUser(@Valid @RequestBody SignUpForm signUpForm, HttpServletRequest request) {
        return authService.SignUp(signUpForm);
    }
    @GetMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        return authService.generateOtpForgotPassword(email);
    }
    @GetMapping("/veritify_otp")
    public ResponseEntity<?> forgotPassword(@RequestParam String email, @RequestParam String otp){
        return authService.veritifyOtpForgotPassword(email,otp);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody RestPassword password,HttpServletRequest request){
        return authService.resetPassword(password,request);
    }
}
