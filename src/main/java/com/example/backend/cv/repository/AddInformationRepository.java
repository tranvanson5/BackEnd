package com.example.backend.cv.repository;


import com.example.backend.cv.model.AddInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddInformationRepository extends JpaRepository<AddInformation,Long> {
}
