package com.example.backend.job.model;

import com.example.backend.job.constain.*;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Job {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {
            @Parameter(name = "variant", value = "timeBased")
    })
    private String id;

    //thông tin job
    private String title;
    @Enumerated(EnumType.STRING)
    private JobExperience jobExperience;
    private BigDecimal startSalary;
    private BigDecimal endSalary;
    private LocalDate recruitmentStartDate;
    private LocalDate recruitmentEndDate;
    private String genderRequest;
    @Column(columnDefinition = "longtext")
    private String jobDescription;
    @Column(columnDefinition = "longtext")
    private String skillDescription;
    @Column(columnDefinition = "longtext")
    private String benefit;
    @Enumerated(EnumType.STRING)
    private JobEducation jobEducation;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    // company detail
    private String company;
    @Column(columnDefinition = "longtext")
    private String companyDescription;
    @Column(columnDefinition = "longtext")
    private String companyLink;
    @Column(columnDefinition = "longtext")
    private String imageUrl;
    @Column(columnDefinition = "longtext")
    private String address;
    @ManyToMany
    @JoinTable(
            name = "Job_Career",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id"))
    Set<Career> careers;

    // tên liên hệ
    private String contactName;
    private String contactAddress;
    private String contactEmail;
    private String contactPhone;
    @Column(columnDefinition = "longtext")
    private String note;

    // thông tin liên quan
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable=false)
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
}
