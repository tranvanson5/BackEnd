package com.example.backend.job.payload.request;

import com.example.backend.job.constain.*;
import com.example.backend.job.model.Career;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class JobForm {
    private String id;
    private String title;
    @Enumerated(EnumType.STRING)
    private JobExperience jobExperience;
    private BigDecimal startSalary;
    private BigDecimal endSalary;
    private LocalDate recruitmentStartDate;
    private LocalDate recruitmentEndDate;
    private String genderRequest;
    private String jobDescription;
    private String skillDescription;
    private String benefit;
    @Enumerated(EnumType.STRING)
    private JobEducation jobEducation;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    // company detail
    private String company;
    private String companyDescription;
    private String companyLink;
    private String imageUrl;
    private String address;
    Set<Long> careers;

    // tên liên hệ
    private String contactName;
    private String contactAddress;
    private String contactEmail;
    private String contactPhone;
    private String note;


    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;
}
