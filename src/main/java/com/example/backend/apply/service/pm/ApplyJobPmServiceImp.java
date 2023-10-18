package com.example.backend.apply.service.pm;

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


@Service
public class ApplyJobPmServiceImp implements ApplyJobPmService {
    @Autowired
    private ApplyJobRepository applyJobRepository;
    @Override
    public ResponseEntity<?> getDataJobApplyJob(String search, ApplyStatus status, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        String statusString = (status != null ? status.toString() : null);
        Page<ApplyJob> applyJobPageable= applyJobRepository.findByUserIdAndSearchByPm(idUser,search,statusString,pageable);
        return new ResponseEntity<>(applyJobPageable, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changeStatus(Long id, ApplyStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = ((UserPrinciple) authentication.getPrincipal()).getId();
        ApplyJob applyJob= applyJobRepository.findByIdPM(id,idUser);
        if (applyJob == null){
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
