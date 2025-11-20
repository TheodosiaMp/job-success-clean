package com.example.jobsuccess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Result {
    private final double score;
    private final String grade;
}

