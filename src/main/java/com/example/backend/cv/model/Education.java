package com.example.backend.cv.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "longtext")
    private String name;
    @Column(columnDefinition = "longtext")
    private String course;
    private String start;
    private String end;
    @Column(columnDefinition = "longtext")
    private String description;

}
