package com.example.backend.apply.service.admin;

import com.example.backend.apply.constain.ApplyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ApplyJobAdminService {
    ResponseEntity<?> getDataJobApplyJob(String search, ApplyStatus status, Pageable pageable);

    ResponseEntity<?> getDataAllJobApplyJob(String search, ApplyStatus status, Pageable pageable);

    ResponseEntity<?> changeStatus(Long id, ApplyStatus status);
}
