package com.example.backend.authen.repository;

import com.example.backend.authen.constain.RoleName;
import com.example.backend.authen.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReposetory extends JpaRepository<Role,String> {
    Optional<Role> findByName(RoleName roleName);
}

