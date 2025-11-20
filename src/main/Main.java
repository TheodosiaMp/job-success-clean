package com.example.jobsuccess;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Config cfg = new Config();

        // default criteria weights (platform) - modify as needed
        Map<String, Double> defaultWeights = new HashMap<>();
        defaultWeights.put("quality", 0.35);
        defaultWeights.put("onTime", 0.25);
        defaultWeights.put("communication", 0.15);
        defaultWeights.put("repeatHire", 0.15);
        defaultWeights.put("disputeResolution", 0.10);

        List<Review> reviews = new ArrayList<>();

        Map<String, Double> c1 = new HashMap<>();
        c1.put("quality", 4.8);
        c1.put("onTime", 5.0);
        c1.put("communication", 4.5);
        reviews.add(new Review(UUID.randomUUID().toString(), c1, 1.5, LocalDate.now().minusDays(10), "Excellent work"));

        Map<String, Double> c2 = new HashMap<>();
        c2.put("quality", 4.2);
        c2.put("onTime", 4.0);
        c2.put("communication", 4.0);
        reviews.add(new Review(UUID.randomUUID().toString(), c2, 1.2, LocalDate.now().minusMonths(8), "Good job"));

        JobSuccessCalculator calc = new JobSuccessCalculator();
        Result r = calc.calculate(reviews, cfg, defaultWeights);
        System.out.println("FINAL -> " + r);
    }
}
