package com.example.backend.apply.repository;

import com.example.backend.apply.model.ApplyJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJob,Long> {
    @Query(value = "SELECT * FROM apply_job " +
            "JOIN job on apply_job.job_id = job.id " +
            "WHERE apply_job.id = :id AND job.user_id = :userId",
            nativeQuery = true)
    ApplyJob findByIdPM(@Param("id") Long id, @Param("userId") String userId);

    List<ApplyJob> findByUserIdApplyAndJobId(String idUser, String jobId);

    @Query(value = "SELECT * FROM apply_job WHERE apply_job.user_id_apply = :idUser " +
            "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%'))"+
            "AND (:status IS NULL OR apply_job.status= :status)",
            countQuery = "SELECT COUNT(*) FROM apply_job WHERE apply_job.user_id_apply = :idUser " +
                    "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%'))"+
                    "AND (:status IS NULL OR apply_job.status= :status)",
            nativeQuery = true)
    Page<ApplyJob> findByUserIdAndSearch(@Param("idUser") String idUser, @Param("search") String search,@Param("status") String status, Pageable pageable);
    @Query(value = "SELECT * FROM apply_job " +
            "JOIN job on apply_job.job_id = job.id " +
            "WHERE job.user_id = :idUser " +  // Added space after :userId
            "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
            "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            countQuery = "SELECT COUNT(*) FROM "+
                    "SELECT * FROM apply_job " +
                    "JOIN job on apply_job.job_id = job.id " +
                    "WHERE job.user_id = :idUser " +  // Added space after :userId
                    "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
                    "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            nativeQuery = true)
    Page<ApplyJob> findByUserIdAndSearchByPm(@Param("idUser") String idUser, @Param("search") String search, @Param("status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM apply_job " +
            "JOIN job on apply_job.job_id = job.id " +
            "WHERE job.user_id = :idUser " +  // Added space after :userId
            "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
            "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            countQuery = "SELECT COUNT(*) FROM "+
                    "SELECT * FROM apply_job " +
                    "JOIN job on apply_job.job_id = job.id " +
                    "WHERE job.user_id = :idUser " +  // Added space after :userId
                    "AND (:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
                    "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            nativeQuery = true)
    Page<ApplyJob> findByUserIdAndSearchByAdmin(@Param("idUser") String idUser, @Param("search") String search, @Param("status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM apply_job " +
            "JOIN job on apply_job.job_id = job.id " +
            "WHERE" +  // Added space after :userId
            "(:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
            "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            countQuery = "SELECT COUNT(*) FROM "+
                    "SELECT * FROM apply_job " +
                    "JOIN job on apply_job.job_id = job.id " +
                    "WHERE " +  // Added space after :userId
                    "(:search IS NULL OR apply_job.id LIKE CONCAT(:search, '%')) " +  // Added spaces after :search
                    "AND (:status IS NULL OR apply_job.status = :status)",  // Added spaces after :status
            nativeQuery = true)
    Page<ApplyJob> findByUserIdAndSearchAllByAdmin(@Param("search") String search, @Param("status") String status, Pageable pageable);
}
