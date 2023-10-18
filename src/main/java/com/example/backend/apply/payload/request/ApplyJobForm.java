package com.example.backend.apply.payload.request;
import lombok.Data;
@Data
public class ApplyJobForm {
    private Long id;
    private String jobId;
    private String urlCv;
    private String cvId;
}
