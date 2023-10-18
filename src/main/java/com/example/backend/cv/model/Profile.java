package com.example.backend.cv.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "longtext")
    private String string;
}
