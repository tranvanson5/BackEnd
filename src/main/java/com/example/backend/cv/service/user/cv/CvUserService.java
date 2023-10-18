package com.example.backend.cv.service.user.cv;

import com.example.backend.cv.payload.request.CreateCvForm;
import org.springframework.http.ResponseEntity;

public interface CvUserService {
    ResponseEntity<?> createCv(CreateCvForm createCvForm);

    ResponseEntity<?> updateCv(CreateCvForm createCvForm);
}
