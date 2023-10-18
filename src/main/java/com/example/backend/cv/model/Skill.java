package com.example.backend.cv.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String name;
    @Column(columnDefinition = "longtext")
    private String description;
}
