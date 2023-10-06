package com.example.backend.job.service.admin;

import com.example.backend.authen.service.userdetail.UserPrinciple;
import com.example.backend.job.constain.*;
import com.example.backend.job.model.Career;
import com.example.backend.job.model.Job;
import com.example.backend.job.payload.request.JobForm;
import com.example.backend.job.payload.response.JobGroupByUser;
import com.example.backend.job.payload.response.JobGroupByUserMonth;
import com.example.backend.job.payload.response.JobGroupByUserYear;
import com.example.backend.job.repository.CareerRepository;
import com.example.backend.job.repository.JobRepository;
import com.example.backend.job.service.user.JobUserSerivceImp;
import com.example.backend.user.model.User;
import com.example.backend.user.payload.response.Count;
import com.example.backend.user.payload.response.CountMoth;
import com.example.backend.user.payload.response.CountYear;
import com.example.backend.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class JobAdminServiceImp implements JobAdminService{
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private JobUserSerivceImp jobUserSerivceImp;
    @Override
    public ResponseEntity<?> getAllDataListJob() {
        List<Job> jobs= jobRepository.findAll();
        if(jobs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createJob(JobForm jobForm) {
        jobForm.setId(null);
        if (jobForm.getCareers() != null && !jobForm.getCareers().isEmpty()) {
            for (Long careerId : jobForm.getCareers()) {
                if (!careerRepository.existsById(careerId)) {
                    return new ResponseEntity<>("Lỗi career", HttpStatus.BAD_REQUEST);
                }
            }
        }
        if(jobForm.getJobStatus()== JobStatus.DELETE){
            return new ResponseEntity<>("Lỗi status", HttpStatus.BAD_REQUEST);
        }
        if (jobForm.getRecruitmentStartDate() != null && jobForm.getRecruitmentEndDate() != null
                && jobForm.getRecruitmentStartDate().isAfter(jobForm.getRecruitmentEndDate())) {
            return new ResponseEntity<>("Lỗi set ngày tuyển", HttpStatus.BAD_REQUEST);
        }

        if (jobForm.getStartSalary() != null && jobForm.getEndSalary() != null
                && jobForm.getStartSalary().compareTo(jobForm.getEndSalary()) > 0) {
            return new ResponseEntity<>("Lỗi set lương", HttpStatus.BAD_REQUEST);
        }

        List<Career> careers = careerRepository.findAllById(jobForm.getCareers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = ((UserPrinciple) authentication.getPrincipal()).getId();
        User user = userRepository.getById(id);

        Job job = new Job();
        BeanUtils.copyProperties(jobForm, job);

        job.setUser(user);
        job.setCareers(new HashSet<>(careers));

        job.setCreateAt(LocalDateTime.now());

        jobRepository.save(job);

        return new ResponseEntity<>("create thông tin job thành công",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateJob(JobForm jobForm) {
        if (jobForm.getId() == null) {
            return new ResponseEntity<>("Lỗi id không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        if (jobForm.getCareers() != null && !jobForm.getCareers().isEmpty()) {
            for (Long careerId : jobForm.getCareers()) {
                if (!careerRepository.existsById(careerId)) {
                    return new ResponseEntity<>("Lỗi career", HttpStatus.BAD_REQUEST);
                }
            }
        }
        if(jobForm.getJobStatus()== JobStatus.DELETE){
            return new ResponseEntity<>("Lỗi status", HttpStatus.BAD_REQUEST);
        }
        if (jobForm.getRecruitmentStartDate() != null && jobForm.getRecruitmentEndDate() != null
                && jobForm.getRecruitmentStartDate().isAfter(jobForm.getRecruitmentEndDate())) {
            return new ResponseEntity<>("Lỗi set ngày tuyển", HttpStatus.BAD_REQUEST);
        }

        if (jobForm.getStartSalary() != null && jobForm.getEndSalary() != null
                && jobForm.getStartSalary().compareTo(jobForm.getEndSalary()) > 0) {
            return new ResponseEntity<>("Lỗi set lương", HttpStatus.BAD_REQUEST);
        }
        if(!jobRepository.existsById(jobForm.getId())){
            return new ResponseEntity<>("Lỗi job không tồn tại", HttpStatus.BAD_REQUEST);
        }
        List<Career> careers = careerRepository.findAllById(jobForm.getCareers());
        Job job= jobRepository.getById(jobForm.getId());
        BeanUtils.copyProperties(jobForm, job);
        job.setCareers(new HashSet<>(careers));
        jobRepository.save(job);
        return new ResponseEntity<>("update thông tin job thành công",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changeStatusJob(String id, JobStatus jobStatus) {
        if(!jobRepository.existsById(id)){
            return new ResponseEntity<>("Lỗi job không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Job job= jobRepository.getById(id);
        job.setJobStatus(jobStatus);
        jobRepository.save(job);
        return new ResponseEntity<>("thay đổi trạng thái job thành công",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllDataListJobBySearch(String search, Pageable pageable) {
        Page<Job> jobs= jobRepository.getAllDataListJobBySearch(search,pageable);

        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJob() {
        Object[] object = jobRepository.getqualityJobAdmin();
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobNoDelete() {
        Object[] object = jobRepository.getqualityJobNoDeleteAdmin();
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobByStatus(JobStatus status) {
        Object[] object = jobRepository.getqualityJobByStatusAdmin(status.toString());
        Count count = new Count();
        BigInteger countValue = (BigInteger) object[0];
        count.setCount(countValue.intValue());
        return new ResponseEntity<>(count, HttpStatus.OK);    }

    @Override
    public ResponseEntity<?> getqualityJobByMoth(int year) {
        List<Object[]> listObject = jobRepository.getQualityJobByMonthAdmin(year);

        List<CountMoth> countMothList = new ArrayList<>();
        for (Object[] obj : listObject) {
            BigInteger month = new BigInteger(obj[0].toString());
            BigInteger count = new BigInteger(obj[1].toString());
            CountMoth countMoth = new CountMoth();
            countMoth.setMoth(month.intValue());
            countMoth.setCount(count.intValue());
            countMothList.add(countMoth);
        }

        return new ResponseEntity<>(countMothList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobByYear() {
        List<Object[]> listObject = jobRepository.getqualityJobByYear();

        List<CountYear> countList = new ArrayList<>();
        for (Object[] obj : listObject) {
            BigInteger year = new BigInteger(obj[0].toString());
            BigInteger count = new BigInteger(obj[1].toString());
            CountYear countYear = new CountYear();
            countYear.setYear(year.intValue());
            countYear.setCount(count.intValue());
            countList.add(countYear);
        }

        return new ResponseEntity<>(countList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobMothByStatus(JobStatus status, int year) {
        List<Object[]> listObject = jobRepository.getQualityJobMothByStatus(status.toString(),year);
        List<CountMoth> countMothList = new ArrayList<>();
        for (Object[] obj : listObject) {
            BigInteger month = new BigInteger(obj[0].toString());
            BigInteger count = new BigInteger(obj[1].toString());
            CountMoth countMoth = new CountMoth();
            countMoth.setMoth(month.intValue());
            countMoth.setCount(count.intValue());
            countMothList.add(countMoth);
        }
        return new ResponseEntity<>(countMothList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getqualityJobYearByStatus(JobStatus status) {
        List<Object[]> listObject = jobRepository.getQualityJobYearByStatus(status.toString());
        List<CountYear> countList = new ArrayList<>();
        for (Object[] obj : listObject) {
            BigInteger year = new BigInteger(obj[0].toString());
            BigInteger count = new BigInteger(obj[1].toString());
            CountYear countYear = new CountYear();
            countYear.setYear(year.intValue());
            countYear.setCount(count.intValue());
            countList.add(countYear);
        }

        return new ResponseEntity<>(countList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> jobGroupByUserBySort(Pageable pageable, String sort) {
        sort = sort.toUpperCase();
        String sortFc = "ASC";
        if(sort.equals("ASC")){
            sortFc = "ASC";
        }else
        if(sort.equals("DESC")){
            sortFc = "DESC";        }
        Page<Object[]> jobs= jobRepository.countByIdAndGroupByUser(pageable,sortFc);
        Page<JobGroupByUser> jobGroupByUserPage = jobs.map(this::mapToJobGroupByUser);
        return new ResponseEntity<>(jobGroupByUserPage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> jobGroupByUserBySortMonth(Pageable pageable, String sort, int year) {
        sort = sort.toUpperCase();
        String sortFc = "ASC";
        if(sort.equals("ASC")){
            sortFc = "ASC";
        }else
        if(sort.equals("DESC")){
            sortFc = "DESC";        }
        Page<Object[]> jobs= jobRepository.jobGroupByUserBySortMonth(pageable,sortFc,year);
        Page<JobGroupByUserYear> jobGroupByUserPage = jobs.map(this::mapToJobGroupByUserYear);
        return new ResponseEntity<>(jobGroupByUserPage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> jobGroupByUserBySortYear(Pageable pageable, String sort) {
        sort = sort.toUpperCase();
        String sortFc = "ASC";
        if(sort.equals("ASC")){
            sortFc = "ASC";
        }else
        if(sort.equals("DESC")){
            sortFc = "DESC";        }
        Page<Object[]> jobs= jobRepository.jobGroupByUserBySortYear(pageable,sortFc);
        Page<JobGroupByUserMonth> jobGroupByUserPage = jobs.map(this::mapToJobGroupByUserMonth);
        return new ResponseEntity<>(jobGroupByUserPage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDataJob(String search, String searchAddress, JobEducation jobEducation, JobExperience jobExperience, JobPosition jobPosition, JobType jobType, String salary, Integer career, JobStatus status, Pageable pageable) {
        return new ResponseEntity<>(jobUserSerivceImp.getAllDataListJob(search, searchAddress,jobEducation,jobExperience,jobPosition, jobType,salary,career,status,pageable),HttpStatus.OK);
    }

    private JobGroupByUserMonth mapToJobGroupByUserMonth(Object[] row) {
        String userId = (String) row[0];
        Long countId = Long.valueOf(row[1].toString());
        int month = Integer.valueOf(row[2].toString());
        return new JobGroupByUserMonth(userId, countId,month);
    }
    private JobGroupByUserYear mapToJobGroupByUserYear(Object[] row) {
        String userId = (String) row[0];
        Long countId = Long.valueOf(row[1].toString());
        int year = Integer.valueOf(row[2].toString());
        return new JobGroupByUserYear(userId, countId,year);
    }
    private JobGroupByUser mapToJobGroupByUser(Object[] row) {
        String userId = (String) row[0];
        Long countId = Long.valueOf(row[1].toString());
        return new JobGroupByUser(userId, countId);
    }


}
