package com.example.backend.apply.service.user;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.apply.payload.request.ApplyJobForm;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ApplyJobService {
    ResponseEntity<?> applyJob(ApplyJobForm applyJob);

    ResponseEntity<?> getDataJobApplyJob(String search, ApplyStatus status, Pageable pageable);

    ResponseEntity<?> cancleApply(Long id, ApplyStatus status);
}
