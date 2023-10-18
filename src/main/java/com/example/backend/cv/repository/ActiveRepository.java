package com.example.backend.cv.repository;

import com.example.backend.cv.model.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveRepository extends JpaRepository<Active,Long> {
}
