package com.example.jobsuccess.modules;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimeDecayModule {
    public static double monthsBetween(LocalDate from, LocalDate to) {
        long days = ChronoUnit.DAYS.between(from, to);
        return days / 30.0;
    }

    public static double decay(double lambda, double months) {
        return Math.exp(-lambda * months);
    }
}
