package com.example.backend.job.payload.response;

import lombok.Data;

@Data
public class JobGroupByUserMonth {
    private String userId;
    private Long countId;
    private int month;

    public JobGroupByUserMonth(String userId, Long countId, int month) {
        this.userId = userId;
        this.countId = countId;
        this.month = month;
    }
}
