package com.example.jobsuccess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class Review {
    private final String id;
    private final Map<String, Double> criteria;
    private final double difficulty;
    private final LocalDate timestamp;
    private final String comment;
}
