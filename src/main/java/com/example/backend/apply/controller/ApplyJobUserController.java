package com.example.backend.apply.controller;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.apply.payload.request.ApplyJobForm;
import com.example.backend.apply.service.user.ApplyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/applyjob")
@CrossOrigin(origins = "*")
public class ApplyJobUserController {
    @Autowired
    private ApplyJobService applyJobService;
    @GetMapping("/getDataJobApplyJob")
    public ResponseEntity<?> getDataJobApplyJob(@RequestParam(required = false) String search,
                                                @RequestParam(required = false) ApplyStatus status,
                                                @PageableDefault Pageable pageable){
        return applyJobService.getDataJobApplyJob(search,status,pageable);
    }
    @PostMapping("/apply")
    public ResponseEntity<?> applyJob(@RequestBody ApplyJobForm applyJob){
        return applyJobService.applyJob(applyJob);
    }
    @PutMapping("/cancleApply")
    public ResponseEntity<?> cancleApply(@RequestParam Long id, @RequestParam ApplyStatus status){
        return applyJobService.cancleApply(id,status);
    }
}
