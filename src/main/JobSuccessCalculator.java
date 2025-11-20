package com.example.jobsuccess;

import com.example.jobsuccess.modules.*;
import java.util.List;
import java.util.stream.Collectors;

public class JobSuccessCalculator {

    public Result calculate(List<Review> reviews, Config cfg, Map<String, Double> defaultWeights) {
        if (reviews == null || reviews.isEmpty()) {
            return new Result(cfg.bayesMu, "NoReviews");
        }

        ReviewProcessor rp = new ReviewProcessor();
        List<ReviewProcessor.AdjustedReview> adjusted = rp.process(reviews, cfg, defaultWeights);

        // weighted aggregation
        double weightedSum = 0.0;
        double weightTotal = 0.0;
        for (ReviewProcessor.AdjustedReview ar : adjusted) {
            weightedSum += ar.baseScore * ar.weight;
            weightTotal += ar.weight;
        }
        double weightedMean = (weightTotal == 0) ? 0.0 : weightedSum / weightTotal;

        // trimmed mean of base scores (for robustness)
        List<Double> baseScores = adjusted.stream().map(ar -> ar.baseScore).collect(Collectors.toList());
        double trimmed = RobustStatistics.trimmedMean(baseScores, cfg.trimPercent);

        // bayesian smoothing
        int n = adjusted.size();
        double bayes = BayesianModule.apply(trimmed, n, cfg.bayesAlpha, cfg.bayesMu);

        // variance stretch + clamp 0..5
        double stretched = VarianceStretchModule.stretch(bayes, cfg.bayesMu, cfg.stretchFactor);

        // audit
        long suspiciousCount = adjusted.stream().filter(ar -> ar.suspicious).count();
        ProcessingAudit audit = new ProcessingAudit(trimmed, bayes, stretched, suspiciousCount, adjusted.size());
        // TODO: save audit in DB / log (we just create it here)

        return new Result(stretched, grade(stretched));
    }

    private String grade(double x) {
        if (x >= 4.5) return "Excellent";
        if (x >= 3.5) return "Good";
        if (x >= 2.5) return "Average";
        if (x >= 1.5) return "Weak";
        return "Poor";
    }
}
