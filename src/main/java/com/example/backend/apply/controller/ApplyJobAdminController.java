package com.example.backend.apply.controller;

import com.example.backend.apply.constain.ApplyStatus;
import com.example.backend.apply.service.admin.ApplyJobAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/applyjob")
@CrossOrigin(origins = "*")
public class ApplyJobAdminController {
    @Autowired
    private ApplyJobAdminService applyJobAdminService;
    @GetMapping("/getDataJobApplyJob")
    public ResponseEntity<?> getDataJobApplyJob(@RequestParam(required = false) String search,
                                                @RequestParam(required = false) ApplyStatus status,
                                                @PageableDefault Pageable pageable){
        return applyJobAdminService.getDataJobApplyJob(search,status,pageable);
    }
    @GetMapping("/getDataAllJobApplyJob")
    public ResponseEntity<?> getDataAllJobApplyJob(@RequestParam(required = false) String search,
                                                @RequestParam(required = false) ApplyStatus status,
                                                @PageableDefault Pageable pageable){
        return applyJobAdminService.getDataAllJobApplyJob(search,status,pageable);
    }
    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam Long id, @RequestParam ApplyStatus status){
        return applyJobAdminService.changeStatus(id,status);
    }
}
