package com.example.backend.user.payload.request;

import com.example.backend.user.constain.EGender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileForm {
    private String name;
    private LocalDate dob;
    private EGender gender;
    private String idCard;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String avatar;
}
