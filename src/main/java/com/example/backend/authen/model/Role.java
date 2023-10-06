package com.example.backend.authen.model;

import com.example.backend.authen.constain.RoleName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Role {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
