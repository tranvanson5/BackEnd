package com.example.backend.cv.repository;

import com.example.backend.cv.model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestsRepository extends JpaRepository<Interests,Long> {
}
