package com.example.jobsuccess.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RobustStatistics {
    public static double trimmedMean(List<Double> values, double trimPercent) {
        if (values == null || values.isEmpty()) return 0.0;
        List<Double> copy = new ArrayList<>(values);
        Collections.sort(copy);
        int n = copy.size();
        int k = (int) Math.floor(n * trimPercent);
        int start = Math.min(k, n);
        int end = Math.max(n - k, start);
        if (start >= end) {
            return copy.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        }
        List<Double> trimmed = copy.subList(start, end);
        return trimmed.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
