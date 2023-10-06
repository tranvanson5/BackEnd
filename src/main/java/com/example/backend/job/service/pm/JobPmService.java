package com.example.backend.job.service.pm;

import com.example.backend.job.constain.*;
import com.example.backend.job.payload.request.JobForm;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface JobPmService {
    ResponseEntity<?> createJob(JobForm jobForm);

    ResponseEntity<?> updateJob(JobForm jobForm);

    ResponseEntity<?> getDataJob(String search, JobEducation jobEducation, JobExperience jobExperience, JobPosition jobPosition, JobType jobType, Integer career, Pageable pageable);

    ResponseEntity<?> getqualityJob();

    ResponseEntity<?> getqualityJobNoDelete();

    ResponseEntity<?> getqualityJobByStatus(JobStatus jobStatus);

    ResponseEntity<?> changeStatusJob(String id, JobStatus jobStatus);
}
