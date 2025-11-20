package com.example.jobsuccess.modules;

import java.util.Map;

public class Normalizer {
    public static double weightedAverageUsingPresent(Map<String, Double> criteriaRatings, Map<String, Double> weights) {
        double weightedSum = 0.0;
        double weightSum = 0.0;
        for (Map.Entry<String, Double> e : criteriaRatings.entrySet()) {
            String key = e.getKey();
            double val = e.getValue();
            double w = weights.getOrDefault(key, 0.0);
            if (w <= 0) continue;
            weightedSum += val * w;
            weightSum += w;
        }
        if (weightSum == 0) {
            return criteriaRatings.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        }
        return weightedSum / weightSum;
    }
}

