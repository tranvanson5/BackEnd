package com.example.backend.job.payload.response;

import lombok.Data;

@Data
public class JobGroupByUserYear {
    private String userId;
    private Long countId;
    private int year;

    public JobGroupByUserYear(String userId, Long countId, int year) {
        this.userId = userId;
        this.countId = countId;
        this.year = year;
    }
}
