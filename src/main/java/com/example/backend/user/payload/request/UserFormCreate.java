package com.example.backend.user.payload.request;

import com.example.backend.user.constain.EGender;
import com.example.backend.user.constain.UserStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Set;
@Data
public class UserFormCreate {
    private String name;
    private LocalDate dob;
    private EGender gender;
    private String idCard;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String avatar;
    private Set<String> roles;
    private UserStatus status;
}
