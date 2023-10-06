package com.example.backend.user.model;


import com.example.backend.authen.model.Role;
import com.example.backend.job.model.Job;
import com.example.backend.user.constain.EGender;
import com.example.backend.user.constain.UserStatus;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {
            @Parameter(name = "variant", value = "timeBased")
    })
    private String id;
    private String name;

    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    private String idCard;
    private String phone;
    @Column(columnDefinition = "longtext")
    private String address;
    @Column(columnDefinition = "longtext")
    private String avatar;



    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Job> jobs;
}
