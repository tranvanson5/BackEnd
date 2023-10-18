package com.example.backend.crawl.model;

import com.example.backend.job.constain.*;
import com.example.backend.job.model.Career;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


@Data
@Entity
public class CrawlJob {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {
            @Parameter(name = "variant", value = "timeBased")
    })
    private String id;
    @Column(columnDefinition = "longtext")
    private String webUrl;
    private String webName;
    @Column(columnDefinition = "longtext")
    private String imageUrl;
    @Column(columnDefinition = "longtext")
    private String title;
    @Column(columnDefinition = "longtext")
    private String company;
    @Column(columnDefinition = "longtext")
    private String companyUrl;
    @Column(columnDefinition = "longtext")
    private String address;
    @Column(columnDefinition = "longtext")
    private String salary;
    @Column(columnDefinition = "longtext")
    private String experience;
    private String recruitmentStartDate;
    private String recruitmentEndDate;
    @Column(columnDefinition = "longtext")
    private String description;
    @Column(columnDefinition = "longtext")
    private String skill;
    @Column(columnDefinition = "longtext")
    private String typeJob;
    @Column(columnDefinition = "longtext")
    private String position;
    @Column(columnDefinition = "longtext")
    private String education;
    private String gender;
    private String age;
    @Column(columnDefinition = "longtext")
    private String career;
    @Column(columnDefinition = "longtext")
    private String contractName;
    @Column(columnDefinition = "longtext")
    private String contractPhone;
    @Column(columnDefinition = "longtext")
    private String contractEmail;
    @Column(columnDefinition = "longtext")
    private String contractAddress;
    @Column(columnDefinition = "longtext")
    private String contractNote;
}
