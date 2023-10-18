package com.example.backend.apply.controller;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.apply.payload.request.ApplyJobForm;
import com.example.backend.apply.service.pm.ApplyJobPmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pm/applyjob")
@CrossOrigin(origins = "*")
public class ApplyJobPmController {
    @Autowired
    private ApplyJobPmService applyJobPmService;
    @GetMapping("/getDataJobApplyJob")
    public ResponseEntity<?> getDataJobApplyJob(@RequestParam(required = false) String search,
                                                @RequestParam(required = false) ApplyStatus status,
                                                @PageableDefault Pageable pageable){
        return applyJobPmService.getDataJobApplyJob(search,status,pageable);
    }
    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam Long id, @RequestParam ApplyStatus status){
        return applyJobPmService.changeStatus(id,status);
    }
}
