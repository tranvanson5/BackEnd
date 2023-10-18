package com.example.backend.apply.service.admin;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.apply.model.ApplyJob;
import com.example.backend.apply.repository.ApplyJobRepository;
import com.example.backend.authen.service.userdetail.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyJobAdminServiceImp implements ApplyJobAdminService{
    @Autowired
    private ApplyJobRepository applyJobRepository;
    @Override
    public ResponseEntity<?> getDataJobApplyJob(String search, ApplyStatus status, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        String statusString = status == null ? null : status.toString();
        Page<ApplyJob> applyJobs = applyJobRepository.findByUserIdAndSearchByAdmin(idUser,search,statusString,pageable);
        return new ResponseEntity<>(applyJobs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDataAllJobApplyJob(String search, ApplyStatus status, Pageable pageable) {
        String statusString = status == null ? null : status.toString();
        Page<ApplyJob> applyJobs = applyJobRepository.findByUserIdAndSearchAllByAdmin(search,statusString,pageable);
        return new ResponseEntity<>(applyJobs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changeStatus(Long id, ApplyStatus status) {
        Optional<ApplyJob> applyJobOptional= applyJobRepository.findById(id);
        ApplyJob applyJob= applyJobOptional.get();
        if (applyJobOptional.isEmpty()){
            return new ResponseEntity<>("Apply Job không tồn tại",HttpStatus.BAD_REQUEST);
        }
        if (applyJob.getStatus()==ApplyStatus.CANCLE){
            return new ResponseEntity<>("Apply đã huỷ không được phép thay đổi",HttpStatus.BAD_REQUEST);
        }
        if(applyJob.getStatus()==ApplyStatus.SUCCESS){
            return new ResponseEntity<>("Apply đã xử lý thành công, không được phép thay đổi",HttpStatus.BAD_REQUEST);
        }
        if(status==ApplyStatus.CANCLE){
            return new ResponseEntity<>("Apply không được phép cancle",HttpStatus.BAD_REQUEST);
        }
        applyJob.setStatus(status);
        applyJobRepository.save(applyJob);
        return new ResponseEntity<>(applyJob.getStatus()+" thành công",HttpStatus.OK);
    }
}
