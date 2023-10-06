package com.example.backend.job.service.user;

import com.example.backend.job.constain.JobEducation;
import com.example.backend.job.constain.JobExperience;
import com.example.backend.job.constain.JobPosition;
import com.example.backend.job.constain.JobType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface JobUserSerivce {
    ResponseEntity<?> getAllDataListJobUserBySearch( String search, String searchAddress,
                                                     JobEducation jobEducation,
                                                     JobExperience jobExperience,
                                                     JobPosition jobPosition,
                                                     JobType jobType,
                                                     String salary,
                                                     Integer career,
                                                     Pageable pageable);
}
