package com.example.backend.job.repository;

import com.example.backend.job.model.Job;
import com.example.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,String> {
//    @Query(
//            value = "SELECT * FROM job " +
//                    "WHERE job.id LIKE CONCAT(:search,'%') " +
//                    "OR job.title LIKE CONCAT('%',:search,'%') " +
//                    "OR job.company LIKE CONCAT('%',:search,'%')",
//            countQuery = "SELECT COUNT(*) FROM " +
//                    "(" +
//                    "SELECT * FROM job " +
//                    "WHERE job.id LIKE CONCAT(:search,'%') " +
//                    "OR job.title LIKE CONCAT('%',:search,'%') " +
//                    "OR job.company LIKE CONCAT('%',:search,'%')" +
//                    ") AS subquery",
//            nativeQuery = true
//    )
//    Page<Job> getAllDataListJobBySearch(@Param("search") String search, Pageable pageable);
    Optional<Job> findByIdAndUser(String id,User user);

    @Query(
            value = "SELECT COUNT(*) FROM job WHERE  job.user_id=userId",
            nativeQuery = true
    )
    Object[] getqualityJobPM(@Param("userId") String userId);
    @Query(
            value = "SELECT COUNT(*) FROM job WHERE job.user_id = :userId AND job.job_status <> 'DELETE'",
            nativeQuery = true
    )
    Object[] getqualityJobNoDelete(@Param("userId") String userId);
    @Query(
            value = "SELECT COUNT(*) FROM job WHERE job.user_id = :userId AND job.job_status = :jobStatus ",
            nativeQuery = true
    )
    Object[] getqualityJobByStatus(@Param("userId") String idUser,@Param("jobStatus") String jobStatus);

    @Query(
            value = "SELECT COUNT(*) FROM job",
            nativeQuery = true
    )
    Object[] getqualityJobAdmin();

    @Query(
            value = "SELECT COUNT(*) FROM job WHERE job.job_status <> 'DELETE'",
            nativeQuery = true
    )
    Object[] getqualityJobNoDeleteAdmin();
    @Query(
            value = "SELECT COUNT(*) FROM job WHERE job.job_status = :jobStatus ",
            nativeQuery = true
    )
    Object[] getqualityJobByStatusAdmin(@Param("jobStatus") String status);
    @Query(
            value = "SELECT MONTH(create_at) AS month, COUNT(*) AS job_count " +
                    "FROM job " +
                    "WHERE YEAR(create_at) = :year " +
                    "GROUP BY MONTH(create_at) " +
                    "ORDER BY MONTH(create_at)",
            nativeQuery = true
    )
    List<Object[]> getQualityJobByMonthAdmin(@Param("year") int year);

    @Query(
            value = "SELECT YEAR(create_at) AS job_year, COUNT(*) AS job_count " +
                    "FROM job " +
                    "GROUP BY job_year " +
                    "ORDER BY job_year",
            nativeQuery = true
    )
    List<Object[]> getqualityJobByYear();
    @Query(
            value = "SELECT YEAR(create_at) AS job_year, COUNT(*) AS job_count " +
                    "FROM job " +
                    "WHERE job.job_status = :status " + // Thêm khoảng cách sau :status
                    "AND YEAR(create_at) = :year " +    // Thêm điều kiện cho năm
                    "GROUP BY job_year " +
                    "ORDER BY job_year",
            nativeQuery = true
    )
    List<Object[]> getQualityJobMothByStatus(@Param("status") String status, @Param("year") int year);

    @Query(
            value = "SELECT YEAR(create_at) AS job_year, COUNT(*) AS job_count " +
                    "FROM job " +
                    "WHERE job.job_status = :status " +
                    "GROUP BY job_year " +
                    "ORDER BY job_year",
            nativeQuery = true
    )
    List<Object[]> getQualityJobYearByStatus(@Param("status") String status);


    @Query(
            value = "SELECT user_id AS userId, COUNT(id) AS countId " +
                    "FROM job GROUP BY job.user_id " +
                    "ORDER BY " +
                    "   CASE WHEN :sort = 'ASC' THEN COUNT(id) END ASC, " +
                    "   CASE WHEN :sort = 'DESC' THEN COUNT(id) END DESC",
            countQuery = "SELECT count(user_id) FROM job",
            nativeQuery = true
    )
    Page<Object[]> countByIdAndGroupByUser(
            Pageable pageable,
            String sort
    );

    @Query(
            value = "SELECT user_id AS userId, COUNT(id) AS countId , MONTH(create_at) AS month " +
                    "FROM job " +
                    "WHERE YEAR(create_at) = :year " + // Filter by year
                    "GROUP BY user_id, MONTH(create_at) " + // Group by both user_id and month
                    "ORDER BY " +
                    "   CASE WHEN :sortFc = 'ASC' THEN COUNT(id) END ASC, " +
                    "   CASE WHEN :sortFc = 'DESC' THEN COUNT(id) END DESC",
            countQuery = "SELECT COUNT(DISTINCT user_id) FROM job " + // Count distinct user_ids
                    "WHERE YEAR(create_at) = :year", // Count query with the same filter
            nativeQuery = true
    )
    Page<Object[]> jobGroupByUserBySortMonth(
            Pageable pageable,
            String sortFc,
            int year
    );
    @Query(
            value = "SELECT user_id AS userId, COUNT(id) AS countId , YEAR(create_at) AS month " +
                    "FROM job " +
                    "GROUP BY user_id, YEAR(create_at) " + // Group by both user_id and month
                    "ORDER BY " +
                    "   CASE WHEN :sortFc = 'ASC' THEN COUNT(id) END ASC, " +
                    "   CASE WHEN :sortFc = 'DESC' THEN COUNT(id) END DESC",
            countQuery = "SELECT COUNT(DISTINCT user_id) FROM job ",// Count distinct user_ids
            nativeQuery = true
    )
    Page<Object[]> jobGroupByUserBySortYear(Pageable pageable, String sortFc);

    @Query(
            value = "SELECT * FROM job " +
                    "LEFT JOIN job_career ON (CASE WHEN :career IS NOT NULL THEN job.id ELSE 1 END) = job_career.job_id " +
                    "WHERE (:search IS NULL OR job.id LIKE CONCAT(:search,'%') OR job.title LIKE CONCAT('%',:search,'%') OR job.company LIKE CONCAT('%',:search,'%')) " +
                    "AND (:searchAddress IS NULL OR job.address LIKE CONCAT('%',:searchAddress,'%')) " +
                    "AND (:jobEducationString IS NULL OR job.job_education = :jobEducationString) " +
                    "AND (:jobExperienceString IS NULL OR job.job_experience = :jobExperienceString) " +
                    "AND (:jobPositionString IS NULL OR job.job_position = :jobPositionString) " +
                    "AND (:jobTypeString IS NULL OR job.job_type = :jobTypeString) " +
                    "AND (:status IS NULL OR job.job_status = :status) " +
                    "AND (:startSalary IS NULL OR job.start_salary <= :startSalary) " +
                    "AND (:endSalary IS NULL OR job.end_salary >= :endSalary) " +
                    "AND (:userId IS NULL OR job.user_id = :userId) " +
                    "AND (:career IS NULL OR job_career.career_id = :career)",
            countQuery = "SELECT count(*) FROM job " +
                    "LEFT JOIN job_career ON (CASE WHEN :career IS NOT NULL THEN job.id ELSE 1 END) = job_career.job_id " +
                    "WHERE (:search IS NULL OR job.id LIKE CONCAT(:search,'%') OR job.title LIKE CONCAT('%',:search,'%') OR job.company LIKE CONCAT('%',:search,'%')) " +
                    "AND (:searchAddress IS NULL OR job.address LIKE CONCAT('%',:searchAddress,'%')) " +
                    "AND (:jobEducationString IS NULL OR job.job_education = :jobEducationString) " +
                    "AND (:jobExperienceString IS NULL OR job.job_experience = :jobExperienceString) " +
                    "AND (:jobPositionString IS NULL OR job.job_position = :jobPositionString) " +
                    "AND (:jobTypeString IS NULL OR job.job_type = :jobTypeString) " +
                    "AND (:status IS NULL OR job.job_status = :status) " +
                    "AND (:startSalary IS NULL OR job.start_salary <= :startSalary) " +
                    "AND (:endSalary IS NULL OR job.end_salary >= :endSalary) " +
                    "AND (:userId IS NULL OR job.user_id = :userId) " +
                    "AND (:career IS NULL OR job_career.career_id = :career)",
            nativeQuery = true
    )
    Page<Job> getDataJob(
            @Param("search") String search,
            @Param("searchAddress") String searchAddress,
            @Param("jobEducationString") String jobEducationString,
            @Param("jobExperienceString") String jobExperienceString,
            @Param("jobPositionString") String jobPositionString,
            @Param("jobTypeString") String jobTypeString,
            @Param("status") String status,
            @Param("career") Integer career,
            @Param("startSalary") BigDecimal startSalary,
            @Param("endSalary") BigDecimal endSalary,
            @Param("userId") String userId,
            Pageable pageable
    );

}
