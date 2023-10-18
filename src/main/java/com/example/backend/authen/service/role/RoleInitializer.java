package com.example.backend.authen.service.role;

import com.example.backend.authen.constain.RoleName;
import com.example.backend.authen.model.Role;
import com.example.backend.authen.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() == 0) {

            for (RoleName roleName : RoleName.values()) {
                String idRole = roleName.toString().replace("ROLE_", "");
                Role role = new Role();
                role.setId(idRole.toLowerCase());
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
