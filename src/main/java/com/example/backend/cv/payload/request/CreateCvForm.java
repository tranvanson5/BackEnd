package com.example.backend.cv.payload.request;

import com.example.backend.cv.model.*;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
public class CreateCvForm {
    private String id;
    private String name;
    private String goal;
    private String imageUrl;
    //profile
    @ManyToMany
    private Set<Profile> profiles = new HashSet<>();
    //education
    private Set<Education> educations = new HashSet<>();
    //    project
    private Set<Project> projects = new HashSet<>();
    //    skills
    private Set<Skill> skills = new HashSet<>();
    //    experience
    private Set<Experience> experiences = new HashSet<>();
    //    certification
    private Set<Certification> certifications = new HashSet<>();
    //    certification
    private Set<Award> awards = new HashSet<>();
    //    active
    private Set<Active> active = new HashSet<>();
    //    interests
    private Set<Interests> interests = new HashSet<>();
    //    references
    private Set<Reference> references = new HashSet<>();
    //    Addition information
    private Set<AddInformation> addInformations = new HashSet<>();
}
