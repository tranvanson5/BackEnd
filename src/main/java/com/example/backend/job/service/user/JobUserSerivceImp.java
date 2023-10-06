package com.example.backend.job.service.user;

import com.example.backend.job.constain.*;
import com.example.backend.job.model.Job;
import com.example.backend.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class JobUserSerivceImp implements JobUserSerivce{
    @Autowired
    private JobRepository jobRepository;

    @Override
    public ResponseEntity<?> getAllDataListJobUserBySearch(String search, String searchAddress,
                                                           JobEducation jobEducation,
                                                           JobExperience jobExperience,
                                                           JobPosition jobPosition,
                                                           JobType jobType,
                                                           String salary,
                                                           Integer carrer,
                                                           Pageable pageable) {
        return new ResponseEntity<>(getAllDataListJob(search,searchAddress,jobEducation,jobExperience,jobPosition,jobType,salary,carrer,JobStatus.ACTIVE,pageable), HttpStatus.OK);
    }
    public Page<?> getAllDataListJob(String search, String searchAddress,
                                     JobEducation jobEducation,
                                     JobExperience jobExperience,
                                     JobPosition jobPosition,
                                     JobType jobType,
                                     String salary,
                                     Integer career,
                                     JobStatus status,
                                     Pageable pageable){
        Page<Job> jobs;
        BigDecimal startSalary;
        BigDecimal endSalary;
        if (search==null){
            search="";
        }
        if (searchAddress==null){
            searchAddress="";
        }

        // xử lý lương
        if(salary==null){
            salary= "";
        }
        switch (salary) {
            case "":
                startSalary = BigDecimal.valueOf(0);
                endSalary = BigDecimal.valueOf(1000000000);
                break;
            case "0":
                startSalary = BigDecimal.valueOf(0);
                endSalary = BigDecimal.valueOf(0);
                break;
            case "1":
                startSalary = BigDecimal.valueOf(0);
                endSalary = BigDecimal.valueOf(10000000);
                break;
            case "2":
                startSalary = BigDecimal.valueOf(10000000);
                endSalary = BigDecimal.valueOf(20000000);
                break;
            case "3":
                startSalary = BigDecimal.valueOf(20000000);
                endSalary = BigDecimal.valueOf(30000000);
                break;
            case "4":
                startSalary = BigDecimal.valueOf(40000000);
                endSalary = BigDecimal.valueOf(50000000);
                break;
            case "5":
                startSalary = BigDecimal.valueOf(50000000);
                endSalary = BigDecimal.valueOf(1000000000);
                break;
            default:
                throw new RuntimeException("Lỗi lương không hợp lệ");
        }


        String jobEducationString = (jobEducation == null) ? "" : jobEducation.toString();
        String jobExperienceString = (jobExperience == null) ? "" : jobExperience.toString();
        String jobPositionString = (jobPosition == null) ? "" : jobPosition.toString();
        String jobTypeString = (jobType == null) ? "" : jobType.toString();
        String jobStatusString = (status == null) ? null : status.toString();

        // Xử lý lọc không theo ngành
        jobs = jobRepository.getAllDataListJobUserBySearch(
                search,
                searchAddress,
                startSalary,
                endSalary,
                jobEducationString,
                jobExperienceString,
                jobPositionString,
                jobTypeString,
                career,
                jobStatusString,
                pageable
        );

        return jobs;
    }
}
