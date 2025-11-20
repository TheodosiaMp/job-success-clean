package com.example.jobsuccess.modules;

public class DifficultyModule {
    // difficulty is a weight (1.0 neutral). Use as-is.
    public static double weight(double difficulty) {
        return Math.max(0.1, difficulty); // minimal floor
    }
}

