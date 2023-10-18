package com.example.backend.cv.repository;

import com.example.backend.cv.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
}
