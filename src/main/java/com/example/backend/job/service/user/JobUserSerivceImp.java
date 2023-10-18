package com.example.backend.job.service.user;

import com.example.backend.job.constain.*;
import com.example.backend.job.model.Job;
import com.example.backend.job.repository.JobRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class JobUserSerivceImp implements JobUserSerivce{
    @Autowired
    private JobRepository jobRepository;

    @Override
    public ResponseEntity<?> getAllDataListJob(String search, String searchAddress,
                                     JobEducation jobEducation,
                                     JobExperience jobExperience,
                                     JobPosition jobPosition,
                                     JobType jobType,
                                     Integer salary,
                                     Integer career,
                                     Pageable pageable){
        BigDecimal startSalary = null;
        BigDecimal endSalary = null;

        if (salary != null) {
            switch (salary) {
                case 1:
                    startSalary = BigDecimal.valueOf(0);
                    endSalary = BigDecimal.valueOf(10000000);
                    break;
                case 2:
                    startSalary = BigDecimal.valueOf(10000000);
                    endSalary = BigDecimal.valueOf(20000000);
                    break;
                case 3:
                    startSalary = BigDecimal.valueOf(20000000);
                    endSalary = BigDecimal.valueOf(30000000);
                    break;
                case 4:
                    startSalary = BigDecimal.valueOf(40000000);
                    endSalary = BigDecimal.valueOf(50000000);
                    break;
                case 5:
                    startSalary = BigDecimal.valueOf(50000000);
                    endSalary = null;
                    break;
                default:
                    return new ResponseEntity<>("Lỗi chon lương",HttpStatus.BAD_REQUEST);
            }
        } else {
            startSalary = null;
            endSalary = null;
        }



        String jobEducationString = (jobEducation == null) ? null : jobEducation.toString();
        String jobExperienceString = (jobExperience == null) ? null : jobExperience.toString();
        String jobPositionString = (jobPosition == null) ? null : jobPosition.toString();
        String jobTypeString = (jobType == null) ? null : jobType.toString();

        Page<Job> jobs= jobRepository.getDataJob(
                search,searchAddress,
                jobEducationString,
                jobExperienceString,
                jobPositionString,
                jobTypeString,
                JobStatus.ACTIVE.toString(),
                career,
                startSalary,
                endSalary,
                null,
                pageable);
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }
}
