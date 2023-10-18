package com.example.backend.cv.service.user.cv;

import com.example.backend.authen.service.userdetail.UserPrinciple;
import com.example.backend.cv.constain.CvStatus;
import com.example.backend.cv.model.*;
import com.example.backend.cv.payload.request.CreateCvForm;
import com.example.backend.cv.repository.*;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CvUserServiceImp implements CvUserService{
    @Autowired
    private CvRepository cvRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private AwardRepository awardRepository;
    @Autowired
    private ActiveRepository activeRepository;
    @Autowired
    private InterestsRepository interestsRepository;
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private AddInformationRepository addInformationRepository;
    @Override
    @Transactional
    public ResponseEntity<?> createCv(CreateCvForm createCvForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        Optional<User> optionalUser = userRepository.findById(idUser);
        User user = optionalUser.get();
        if (createCvForm.getId() != null) {
            createCvForm.setId(null);
        }
        Cv cv= new Cv();
        cv.setImageUrl(createCvForm.getImageUrl());
        cv.setName(createCvForm.getName());
        cv.setGoal(createCvForm.getGoal());

        // Clear and set for profiles
        if (!createCvForm.getProfiles().isEmpty()) {
            List<Profile> profiles = new ArrayList<>();
            for (Profile profile : createCvForm.getProfiles()) {
                profile.setId(null);
                profiles.add(profile);
            }
            profileRepository.saveAll(profiles);
            cv.setProfiles(new HashSet<>(profiles));
        }

// Clear and set for educations
        if (!createCvForm.getEducations().isEmpty()) {
            List<Education> educations = new ArrayList<>();
            for (Education education : createCvForm.getEducations()) {
                education.setId(null);
                educations.add(education);
            }
            educationRepository.saveAll(educations);
            cv.setEducations(new HashSet<>(educations));
        }

// Clear and set for projects
        if (!createCvForm.getProjects().isEmpty()) {
            List<Project> projects = new ArrayList<>();
            for (Project project : createCvForm.getProjects()) {
                project.setId(null);
                projects.add(project);
            }
            projectRepository.saveAll(projects);
            cv.setProjects(new HashSet<>(projects));
        }

// Clear and set for skills
        if (!createCvForm.getSkills().isEmpty()) {
            List<Skill> skills = new ArrayList<>();
            for (Skill skill : createCvForm.getSkills()) {
                skill.setId(null);
                skills.add(skill);
            }
            skillRepository.saveAll(skills);
            cv.setSkills(new HashSet<>(skills));
        }

// Clear and set for experiences
        if (!createCvForm.getExperiences().isEmpty()) {
            List<Experience> experiences = new ArrayList<>();
            for (Experience experience : createCvForm.getExperiences()) {
                experience.setId(null);
                experiences.add(experience);
            }
            experienceRepository.saveAll(experiences);
            cv.setExperiences(new HashSet<>(experiences));
        }

// Clear and set for certifications
        if (!createCvForm.getCertifications().isEmpty()) {
            List<Certification> certifications = new ArrayList<>();
            for (Certification certification : createCvForm.getCertifications()) {
                certification.setId(null);
                certifications.add(certification);
            }
            certificationRepository.saveAll(certifications);
            cv.setCertifications(new HashSet<>(certifications));
        }

// Clear and set for awards
        if (!createCvForm.getAwards().isEmpty()) {
            List<Award> awards = new ArrayList<>();
            for (Award award : createCvForm.getAwards()) {
                award.setId(null);
                awards.add(award);
            }
            awardRepository.saveAll(awards);
            cv.setAwards(new HashSet<>(awards));
        }

// Clear and set for active
        if (!createCvForm.getActive().isEmpty()) {
            List<Active> activeList = new ArrayList<>();
            for (Active activeItem : createCvForm.getActive()) {
                activeItem.setId(null);
                activeList.add(activeItem);
            }
            activeRepository.saveAll(activeList);
            cv.setActive(new HashSet<>(activeList));
        }

// Clear and set for interests
        if (!createCvForm.getInterests().isEmpty()) {
            List<Interests> interestsList = new ArrayList<>();
            for (Interests interestsItem : createCvForm.getInterests()) {
                interestsItem.setId(null);
                interestsList.add(interestsItem);
            }
            interestsRepository.saveAll(interestsList);
            cv.setInterests(new HashSet<>(interestsList));
        }

// Clear and set for references
        if (!createCvForm.getReferences().isEmpty()) {
            List<Reference> referencesList = new ArrayList<>();
            for (Reference reference : createCvForm.getReferences()) {
                reference.setId(null);
                referencesList.add(reference);
            }
            referenceRepository.saveAll(referencesList);
            cv.setReferences(new HashSet<>(referencesList));
        }

// Clear and set for addInformations
        if (!createCvForm.getAddInformations().isEmpty()) {
            List<AddInformation> addInformationsList = new ArrayList<>();
            for (AddInformation addInformation : createCvForm.getAddInformations()) {
                addInformation.setId(null);
                addInformationsList.add(addInformation);
            }
            addInformationRepository.saveAll(addInformationsList);
            cv.setAddInformations(new HashSet<>(addInformationsList));
        }
        cv.setCreateAt(LocalDateTime.now());
        cv.setStatus(CvStatus.ACTIVE);
        cv.setUser(user);
        cvRepository.save(cv);
        return new ResponseEntity<>("create cv thành công", HttpStatus.OK);
    }


    @Override
    public ResponseEntity < ? > updateCv(CreateCvForm createCvForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        Optional < User > optionalUser = userRepository.findById(idUser);
        User user = optionalUser.get();
        if (createCvForm.getId() == null) {
            throw new RuntimeException("Id cv không hợp lệ");
        }
        Optional < Cv > optionalCv = cvRepository.findByIdAndUser(createCvForm.getId(), user);
        if (optionalCv.isEmpty()) {
            throw new RuntimeException("CV không tồn tại");
        }
        Cv cv = optionalCv.get();
        cv.setImageUrl(createCvForm.getImageUrl());
        cv.setName(createCvForm.getName());
        cv.setGoal(createCvForm.getGoal());

        List < Profile > profiles = new ArrayList < > ();
        for (Profile profile: createCvForm.getProfiles()) {
            profile.setId(null);
            profiles.add(profile);
        }
        List < Profile > profilesRemove = new ArrayList < > ();
        profilesRemove.addAll(cv.getProfiles());
        cv.getProfiles().clear();
        profileRepository.deleteAll(profilesRemove);
        profileRepository.saveAll(profiles);
        cv.setProfiles(new HashSet < > (profiles));
        // Clear and set for educations
        List < Education > educations = new ArrayList < > ();
        for (Education education: createCvForm.getEducations()) {
            education.setId(null);
            educations.add(education);
        }
        List < Education > educationRemove = new ArrayList < > ();
        educationRemove.addAll(cv.getEducations());
        cv.getEducations().clear();
        educationRepository.deleteAll(educationRemove);
        educationRepository.saveAll(educations);
        cv.setEducations(new HashSet < > (educations));

        // Clear and set for projects
        List < Project > projectRemove = new ArrayList < > ();
        List < Project > projects = new ArrayList < > ();
        for (Project project: createCvForm.getProjects()) {
            project.setId(null);
            projects.add(project);
        }
        projectRemove.addAll(cv.getProjects());
        cv.getProjects().clear();
        projectRepository.deleteAll(projectRemove);
        projectRepository.saveAll(projects);
        cv.setProjects(new HashSet < > (projects));

        // Clear and set for skills
        List < Skill > skills = new ArrayList < > ();
        List < Skill > skillRemove = new ArrayList < > ();
        for (Skill skill: createCvForm.getSkills()) {
            skill.setId(null);
            skills.add(skill);
        }
        skillRemove.addAll(cv.getSkills());
        cv.getSkills().clear();
        skillRepository.deleteAll(skillRemove);
        skillRepository.saveAll(skills);
        cv.setSkills(new HashSet < > (skills));

        // Clear and set for experiences
        List < Experience > experiences = new ArrayList < > ();
        List < Experience > experienceRemove = new ArrayList < > ();
        for (Experience experience: createCvForm.getExperiences()) {
            experience.setId(null);
            experiences.add(experience);
        }
        experienceRemove.addAll(cv.getExperiences());
        cv.getExperiences().clear();
        experienceRepository.deleteAll(experienceRemove);
        experienceRepository.saveAll(experiences);
        cv.setExperiences(new HashSet < > (experiences));

        // Clear and set for certifications
        List < Certification > certifications = new ArrayList < > ();
        List < Certification > certificationRemove = new ArrayList < > ();

        for (Certification certification: createCvForm.getCertifications()) {
            certification.setId(null);
            certifications.add(certification);
        }
        certificationRemove.addAll(cv.getCertifications());
        cv.getCertifications().clear();
        certificationRepository.deleteAll(certificationRemove);
        certificationRepository.saveAll(certifications);
        cv.setCertifications(new HashSet < > (certifications));

        // Clear and set for awards
        List < Award > awards = new ArrayList < > ();
        List < Award > awardRemove = new ArrayList < > ();
        for (Award award: createCvForm.getAwards()) {
            award.setId(null);
            awards.add(award);
        }
        awardRemove.addAll(cv.getAwards());
        cv.getAwards().clear();
        awardRepository.deleteAll(awardRemove);
        awardRepository.saveAll(awards);
        cv.setAwards(new HashSet < > (awards));

        // Clear and set for active
        List < Active > activeList = new ArrayList < > ();
        List < Active > activeRemove = new ArrayList < > ();
        for (Active activeItem: createCvForm.getActive()) {
            activeItem.setId(null);
            activeList.add(activeItem);
        }
        activeRemove.addAll(cv.getActive());
        cv.getActive().clear();
        activeRepository.deleteAll(activeRemove);
        activeRepository.saveAll(activeList);
        cv.setActive(new HashSet < > (activeList));

        // Clear and set for interests
        List < Interests > interestsList = new ArrayList < > ();
        List < Interests > interesrsRemove = new ArrayList < > ();
        for (Interests interestsItem: createCvForm.getInterests()) {
            interestsItem.setId(null);
            interestsList.add(interestsItem);
        }
        interesrsRemove.addAll(cv.getInterests());
        cv.getInterests().clear();
        interestsRepository.deleteAll(interesrsRemove);
        interestsRepository.saveAll(interestsList);
        cv.setInterests(new HashSet < > (interestsList));

        // Clear and set for references
        List < Reference > referencesList = new ArrayList < > ();
        List < Reference > referencesRemove = new ArrayList < > ();
        for (Reference reference: createCvForm.getReferences()) {
            reference.setId(null);
            referencesList.add(reference);
        }
        referencesRemove.addAll(cv.getReferences());
        cv.getReferences().clear();
        referenceRepository.deleteAll(referencesRemove);
        referenceRepository.saveAll(referencesList);
        cv.setReferences(new HashSet < > (referencesList));

        // Clear and set for addInformations
        List < AddInformation > addInformationsList = new ArrayList < > ();
        List < AddInformation > addInformationsRemove = new ArrayList < > ();
        for (AddInformation addInformation: createCvForm.getAddInformations()) {
            addInformation.setId(null);
            addInformationsList.add(addInformation);
        }
        addInformationsRemove.addAll(cv.getAddInformations());
        cv.getAddInformations().clear();
        addInformationRepository.deleteAll(addInformationsRemove);
        addInformationRepository.saveAll(addInformationsList);
        cv.setAddInformations(new HashSet < > (addInformationsList));
        cv.setUser(user);
        cvRepository.save(cv);
        return new ResponseEntity < > (cv, HttpStatus.OK);
    }
}
