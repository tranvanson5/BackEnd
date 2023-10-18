package com.example.backend.job.service.admin;

import com.example.backend.job.constain.*;
import com.example.backend.job.payload.request.JobForm;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface JobAdminService {
//    ResponseEntity<?> getAllDataListJob();

    ResponseEntity<?> createJob(JobForm jobForm);

    ResponseEntity<?> updateJob(JobForm jobForm);
    ResponseEntity<?> changeStatusJob(String id, JobStatus jobStatus);

//    ResponseEntity<?> getAllDataListJobBySearch(String search, Pageable pageable);

    ResponseEntity<?> getqualityJob();

    ResponseEntity<?> getqualityJobNoDelete();

    ResponseEntity<?> getqualityJobByStatus(JobStatus status);

    ResponseEntity<?> getqualityJobByMoth(int year);

    ResponseEntity<?> getqualityJobByYear();

    ResponseEntity<?> getqualityJobMothByStatus(JobStatus status, int year);

    ResponseEntity<?> getqualityJobYearByStatus(JobStatus status);

    ResponseEntity<?> jobGroupByUserBySort(Pageable pageable, String sort);

    ResponseEntity<?> jobGroupByUserBySortMonth(Pageable pageable, String sort, int year);

    ResponseEntity<?> jobGroupByUserBySortYear(Pageable pageable, String sort);

    ResponseEntity<?> getDataJob(String search, String searchAddress, JobEducation jobEducation, JobExperience jobExperience, JobPosition jobPosition, JobType jobType, Integer salary, Integer career, JobStatus status, String userId,Pageable pageable);
}
