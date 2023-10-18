package com.example.backend.apply.model;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.job.model.Job;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class ApplyJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userIdApply;

    @ManyToOne(fetch = FetchType.EAGER) // Set optional to false if it's mandatory
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    private Job job;

    @Column(columnDefinition = "longtext")
    private String urlCv;

    private String cvId;

    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private ApplyStatus status;
}
