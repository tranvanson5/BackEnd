package com.example.backend.cv.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String time;
    @Column(columnDefinition = "longtext")
    private String name;
    @Column(columnDefinition = "longtext")
    private String descripton;
}
