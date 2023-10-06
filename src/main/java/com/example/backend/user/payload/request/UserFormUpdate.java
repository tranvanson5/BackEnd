package com.example.backend.user.payload.request;

import com.example.backend.user.constain.EGender;
import com.example.backend.user.constain.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
public class UserFormUpdate {
    private String id;
    private String name;
    private LocalDate dob;
    private EGender gender;
    private String idCard;
    private String phone;
    private String address;
    private String avatar;
    private Set<String> roles;
    private UserStatus status;
}
