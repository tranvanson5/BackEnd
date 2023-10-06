package com.example.backend.job.payload.response;

import lombok.Data;


@Data
public class JobGroupByUser {
    private String userId;
    private Long countId;

    public JobGroupByUser(String userId, Long countId) {
        this.userId = userId;
        this.countId = countId;
    }
}
