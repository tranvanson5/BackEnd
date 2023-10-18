package com.example.backend.job.service.pm;

import com.example.backend.authen.service.userdetail.UserPrinciple;
import com.example.backend.job.constain.*;
import com.example.backend.job.model.Job;
import com.example.backend.job.payload.request.JobForm;
import com.example.backend.job.repository.CareerRepository;
import com.example.backend.job.repository.JobRepository;
import com.example.backend.job.service.admin.JobAdminService;
import com.example.backend.user.model.User;
import com.example.backend.user.payload.response.Count;
import com.example.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class JobPmServiceImp implements JobPmService{
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private JobAdminService jobAdminService;
    @Override
    public ResponseEntity<?> createJob(JobForm jobForm) {
        return jobAdminService.createJob(jobForm);
    }
    @Override
    public ResponseEntity<?> changeStatusJob(String id, JobStatus jobStatus) {
        if(jobStatus==JobStatus.DELETE){
            throw new RuntimeException("Bạn không khôi phục job");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        Optional<User> user= userRepository.findById(idUser);
        if (user.isEmpty()){
            return new ResponseEntity<>("User không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<Job> job= jobRepository.findByIdAndUser(id, user.get());
        if(job.isEmpty()){
            return new ResponseEntity<>("Job không tồn tại", HttpStatus.BAD_REQUEST);
        }
        job.get().setJobStatus(jobStatus);
        jobRepository.save(job.get());
        return new ResponseEntity<>("thay đổi trạng thái job thành công",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> updateJob(JobForm jobForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        Optional<User> user= userRepository.findById(idUser);
        if (user.isEmpty()){
            return new ResponseEntity<>("User không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<Job> job= jobRepository.findByIdAndUser(jobForm.getId(), user.get());
        if(job.isEmpty()){
            return new ResponseEntity<>("Job không tồn tại", HttpStatus.BAD_REQUEST);
        }
        return jobAdminService.updateJob(jobForm);
    }

    @Override
    public ResponseEntity<?> getDataJob(String search,String searchAddress, JobEducation jobEducation, JobExperience jobExperience, JobPosition jobPosition, JobType jobType,JobStatus status,Integer career,Integer salary,Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String idUser = principal.getId();
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
        String jobStatusString = (jobType == null) ? null : status.toString();
        Page<Job> jobs= jobRepository.getDataJob(
                search,searchAddress,
                jobEducationString,
                jobExperienceString,
                jobPositionString,
                jobTypeString,
                jobStatusString,
                career,
                startSalary,
                endSalary,
                idUser,
                pageable);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJob() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String idUser = principal.getId();
        Object[] object = jobRepository.getqualityJobPM(idUser);
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobNoDelete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String idUser = principal.getId();
        Object[] object = jobRepository.getqualityJobNoDelete(idUser);
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobByStatus(JobStatus jobStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String idUser = principal.getId();
        Object[] object = jobRepository.getqualityJobByStatus(idUser,jobStatus.toString());
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);
    }




}
