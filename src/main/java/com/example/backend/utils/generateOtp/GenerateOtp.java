package com.example.backend.utils.generateOtp;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateOtp {
    private final Random random = new Random();
    public String generateOtp() {
        // Tạo OTP có độ dài 6 chữ số
        int otp = 100000 + random.nextInt(900000);

        // Đổi OTP thành chuỗi
        String otpStr = String.valueOf(otp);

        // Trả về OTP
        return otpStr;
    }
}
