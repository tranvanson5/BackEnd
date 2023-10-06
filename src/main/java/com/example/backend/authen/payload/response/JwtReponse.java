package com.example.backend.authen.payload.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtReponse {
    private String jwt;

    private String id;

    private String name;

    private String username;

    private String email;

    private Collection<? extends GrantedAuthority> roles;

    public JwtReponse(String jwt, String id, String name, String username, String email, Collection<? extends GrantedAuthority> roles) {
        this.jwt = jwt;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
