package com.example.backend.job.repository;

import com.example.backend.job.model.Job;
import com.example.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,String> {
    @Query(
            value = "SELECT * FROM job " +
                    "WHERE job.id LIKE CONCAT(:search,'%') " +
                    "OR job.title LIKE CONCAT('%',:search,'%') " +
                    "OR job.company LIKE CONCAT('%',:search,'%')",
            countQuery = "SELECT COUNT(*) FROM " +
                    "(" +
                    "SELECT * FROM job " +
                    "WHERE job.id LIKE CONCAT(:search,'%') " +
                    "OR job.title LIKE CONCAT('%',:search,'%') " +
                    "OR job.company LIKE CONCAT('%',:search,'%')" +
                    ") AS subquery",
            nativeQuery = true
    )
    Page<Job> getAllDataListJobBySearch(@Param("search") String search, Pageable pageable);
    @Query(
            value =
                    "SELECT DISTINCT j.* FROM job j " +
                            "LEFT JOIN job_career jc ON j.id = jc.job_id AND :career IS NOT NULL " +
                            "WHERE " +
                            "   ((j.id LIKE CONCAT(:search, '%') OR j.title LIKE CONCAT('%', :search, '%') OR j.company LIKE CONCAT('%', :search, '%'))) " +
                            "   AND j.address LIKE CONCAT('%', :searchAddress, '%') " +
                            "   AND j.start_salary >= :startSalary " +
                            "   AND j.end_salary <= :endSalary " +
                            "   AND j.job_education LIKE CONCAT('%', :jobEducationString, '%') " +
                            "   AND j.job_experience LIKE CONCAT('%', :jobExperienceString, '%') " +
                            "   AND j.job_position LIKE CONCAT('%', :jobPositionString, '%') " +
                            "   AND j.job_type LIKE CONCAT('%', :jobTypeString, '%') " +
                            "   AND (j.job_status = :active OR (:active IS NULL AND (j.job_status = 'BLOCK' OR j.job_status = 'ACTIVE')))",
            countQuery =
                    "SELECT COUNT(*) FROM (" +
                            "   SELECT DISTINCT j.* FROM job j " +
                            "   LEFT JOIN job_career jc ON j.id = jc.job_id AND :career IS NOT NULL " +
                            "   WHERE " +
                            "       ((j.id LIKE CONCAT(:search, '%') OR j.title LIKE CONCAT('%', :search, '%') OR j.company LIKE CONCAT('%', :search, '%'))) " +
                            "       AND j.address LIKE CONCAT('%', :searchAddress, '%') " +
                            "       AND j.start_salary >= :startSalary " +
                            "       AND j.end_salary <= :endSalary " +
                            "       AND j.job_education LIKE CONCAT('%', :jobEducationString, '%') " +
                            "       AND j.job_experience LIKE CONCAT('%', :jobExperienceString, '%') " +
                            "       AND j.job_position LIKE CONCAT('%', :jobPositionString, '%') " +
                            "       AND j.job_type LIKE CONCAT('%', :jobTypeString, '%')" +
                            "       AND (j.job_status = :active OR (:active IS NULL AND (j.job_status = 'BLOCK' OR j.job_status = 'ACTIVE')))" +
                            ") AS subquery",
            nativeQuery = true
    )
    Page<Job> getAllDataListJobUserBySearch(
            @Param("search") String search,
            @Param("searchAddress") String searchAddress,
            @Param("startSalary") BigDecimal startSalary,
            @Param("endSalary") BigDecimal endSalary,
            @Param("jobEducationString") String jobEducationString,
            @Param("jobExperienceString") String jobExperienceString,
            @Param("jobPositionString") String jobPositionString,
            @Param("jobTypeString") String jobTypeString,
            @Param("career") Integer career,
            @Param("active") String active,
            Pageable pageable
    );







    @Query(
            value =
                    "SELECT * FROM job " +
                            "INNER JOIN job_career ON job.id = job_career.job_id " +
                            "WHERE " +
                            "( " +
                            "   (job.id LIKE CONCAT(:search, '%') " +
                            "   OR job.title LIKE CONCAT('%', :search, '%') " +
                            "   OR job.company LIKE CONCAT('%', :search, '%') " +
                            "   OR job.address LIKE CONCAT('%', :searchAddress, '%') " +
                            ") " +
                            "       AND (:jobEducation IS NULL OR job.job_education LIKE CONCAT('%', :jobEducation, '%')) " +
                            "       AND (:jobExperience IS NULL OR job.job_experience LIKE CONCAT('%', :jobExperience, '%'))" +
                            "       AND (:jobPosition IS NULL OR job.job_position LIKE CONCAT('%', :jobPosition, '%'))" +
                            "       AND (:jobType IS NULL OR job.job_type LIKE CONCAT('%', :jobType, '%'))" +
                            "       AND (job.start_salary >= :startSalary AND job.end_salary <= :endSalary)" +
                            "       AND (job_career.career_id = :career)" +
                            "AND job.job_status= :status" +
                            ")",
            countQuery =
                    "SELECT COUNT(*) FROM (" +
                            "   SELECT * FROM job " +
                            "   INNER JOIN job_career ON job.id = job_career.job_id " +
                            "   WHERE " +
                            "   ( " +
                            "       job.id LIKE CONCAT(:search, '%') " +
                            "       OR job.title LIKE CONCAT('%', :search, '%') " +
                            "       OR job.company LIKE CONCAT('%', :search, '%') " +
                            "       OR job.address LIKE CONCAT('%', :searchAddress, '%') " +
                            "   ) " +
                            "   AND (:jobEducation IS NULL OR job.job_education LIKE CONCAT('%', :jobEducation, '%')) " +
                            "   AND (:jobExperience IS NULL OR job.job_experience LIKE CONCAT('%', :jobExperience, '%')) " +
                            "   AND (:jobPosition IS NULL OR job.job_position LIKE CONCAT('%', :jobPosition, '%')) " +
                            "   AND (:jobType IS NULL OR job.job_type LIKE CONCAT('%', :jobType, '%')) " +
                            "   AND (job.start_salary >= :startSalary AND job.end_salary <= :endSalary)" +
                            "   AND (job_career.career_id = :career)" +
                            "   AND job.job_status= :status" +
                            ") AS subquery",
            nativeQuery = true
    )
    Page<Job> getAllDataListJobUserBySearchByCareer(
            @Param("search") String search,
            @Param("searchAddress") String searchAddress,
            @Param("jobEducation") String jobEducation,
            @Param("jobExperience") String jobExperience,
            @Param("jobPosition") String jobPosition,
            @Param("jobType") String jobType,
            @Param("startSalary") BigDecimal startSalary,
            @Param("endSalary") BigDecimal endSalary,
            @Param("career") int career,
            @Param("status") String status,
            Pageable pageable
    );
    Optional<Job> findByIdAndUser(String id,User user);
    @Query(
            value = "SELECT * FROM job " +
                    "WHERE user_id = :idUser " +
                    "AND (job.id LIKE CONCAT(:search, '%') OR job.title LIKE CONCAT('%', :search, '%') OR job.company LIKE CONCAT('%', :search, '%') OR job.address LIKE CONCAT('%', :search, '%'))" +
                    "AND (job.job_education LIKE CONCAT('%', :jobEducationString, '%') AND job.job_experience LIKE CONCAT('%', :jobExperienceString, '%') AND job.job_position LIKE CONCAT('%', :jobPositionString, '%') AND job.job_type LIKE CONCAT('%', :jobTypeString, '%'))" +
                    "AND job.job_status <> 'DELETE'",
            countQuery = "SELECT COUNT(*) FROM job " +
                    "WHERE user_id = :idUser " +
                    "AND (job.id LIKE CONCAT(:search, '%') OR job.title LIKE CONCAT('%', :search, '%') OR job.company LIKE CONCAT('%', :search, '%') OR job.address LIKE CONCAT('%', :search, '%'))" +
                    "AND (job.job_education LIKE CONCAT('%', :jobEducationString, '%') AND job.job_experience LIKE CONCAT('%', :jobExperienceString, '%') AND job.job_position LIKE CONCAT('%', :jobPositionString, '%') AND job.job_type LIKE CONCAT('%', :jobTypeString, '%'))" +
                    "AND job.job_status <> 'DELETE'",
            nativeQuery = true
    )
    Page<Job> getDataJobByPm(
            @Param("search") String search,
            @Param("jobEducationString") String jobEducationString,
            @Param("jobExperienceString") String jobExperienceString,
            @Param("jobPositionString") String jobPositionString,
            @Param("jobTypeString") String jobTypeString,
            @Param("idUser") String idUser,
            Pageable pageable
    );
    @Query(
            value = "SELECT * FROM job " +
                    "INNER JOIN job_career ON job.id = job_career.job_id " +
                    "WHERE user_id = :idUser " +
                    "AND (job.id LIKE CONCAT(:search, '%') OR job.title LIKE CONCAT('%', :search, '%') OR job.company LIKE CONCAT('%', :search, '%') OR job.address LIKE CONCAT('%', :search, '%'))" +
                    "AND (job.job_education LIKE CONCAT('%', :jobEducationString, '%') AND job.job_experience LIKE CONCAT('%', :jobExperienceString, '%') AND job.job_position LIKE CONCAT('%', :jobPositionString, '%') AND job.job_type LIKE CONCAT('%', :jobTypeString, '%'))" +
                    "AND job_career.career_id = :career " +
                    "AND job.job_status <> 'DELETE'",
            countQuery = "SELECT COUNT(*) FROM job " +
                    "INNER JOIN job_career ON job.id = job_career.job_id " +
                    "WHERE user_id = :idUser " +
                    "AND (job.id LIKE CONCAT(:search, '%') OR job.title LIKE CONCAT('%', :search, '%') OR job.company LIKE CONCAT('%', :search, '%') OR job.address LIKE CONCAT('%', :search, '%'))" +
                    "AND (job.job_education LIKE CONCAT('%', :jobEducationString, '%') AND job.job_experience LIKE CONCAT('%', :jobExperienceString, '%') AND job.job_position LIKE CONCAT('%', :jobPositionString, '%') AND job.job_type LIKE CONCAT('%', :jobTypeString, '%'))" +
                    "AND job_career.career_id = :career " +
                    "AND job.job_status <> 'DELETE'",
            nativeQuery = true
    )
    Page<Job> getDataJobByPmCareer(
            @Param("search") String search,
            @Param("jobEducationString") String jobEducationString,
            @Param("jobExperienceString") String jobExperienceString,
            @Param("jobPositionString") String jobPositionString,
            @Param("jobTypeString") String jobTypeString,
            @Param("career") Integer career,
            @Param("idUser") String idUser,
            Pageable pageable
    );

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
}
