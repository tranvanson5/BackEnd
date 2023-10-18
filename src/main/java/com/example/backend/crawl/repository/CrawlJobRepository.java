package com.example.backend.crawl.repository;

import com.example.backend.crawl.model.CrawlJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlJobRepository extends JpaRepository<CrawlJob, String> {
    Boolean existsByWebUrl(String url);
    Page<CrawlJob> findAll(Pageable pageable);
}
