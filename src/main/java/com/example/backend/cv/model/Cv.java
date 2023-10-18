package com.example.backend.cv.model;

import com.example.backend.cv.constain.CvStatus;
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
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Cv {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {
            @Parameter(name = "variant", value = "timeBased")
    })
    private String id;
    private String name;
    @Column(columnDefinition = "longtext")
    private String goal;
    @Column(columnDefinition = "longtext")
    private String imageUrl;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_profile",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profiles = new HashSet<>();

    //education
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_education",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "education_id")
    )
    private Set<Education> educations = new HashSet<>();
    //    project
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_project",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();
//    skills
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_skill",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    //    experience
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_experience",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id")
    )
    private Set<Experience> experiences = new HashSet<>();

    //    certification
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_certification",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "certification_id")
    )
    private Set<Certification> certifications = new HashSet<>();

    //    certification
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_award",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "award_id")
    )
    private Set<Award> awards = new HashSet<>();
    //    active
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_active",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "active_id")
    )
    private Set<Active> active = new HashSet<>();


    //    interests
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_interests",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "interests_id")
    )
    private Set<Interests> interests = new HashSet<>();
    //    references
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_reference",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "references_id")
    )
    private Set<Reference> references = new HashSet<>();

    //    Addition information
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "cv_add_informations",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "add_informations_id")
    )
    private Set<AddInformation> addInformations = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable=false)
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private CvStatus status;
}
