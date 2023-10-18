package com.example.backend.cv.repository;

import com.example.backend.cv.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award,Long> {
}
