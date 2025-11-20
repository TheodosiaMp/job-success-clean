package com.example.jobsuccess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ProcessingAudit {
    private final double trimmed;
    private final double bayes;
    private final double stretched;
    private final long suspicious;
    private final int count;
    private final LocalDateTime ts = LocalDateTime.now();
}
