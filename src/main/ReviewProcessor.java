ppackage com.example.jobsuccess;

import com.example.jobsuccess.modules.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReviewProcessor {

    public static class AdjustedReview {
        public final Review review;
        public final double baseScore;
        public final double weight;
        public final boolean suspicious;

        public AdjustedReview(Review review, double baseScore, double weight, boolean suspicious) {
            this.review = review;
            this.baseScore = baseScore;
            this.weight = weight;
            this.suspicious = suspicious;
        }
    }

    public List<AdjustedReview> process(List<Review> reviews, Config cfg, Map<String, Double> defaultWeights) {
        LocalDate now = LocalDate.now();
        List<AdjustedReview> out = new ArrayList<>();

        // precompute bases
        Map<Review, Double> baseMap = new HashMap<>();
        for (Review r : reviews) {
            double base = Normalizer.weightedAverageUsingPresent(r.getCriteria(), defaultWeights);
            baseMap.put(r, base);
        }

        // collect comments/dates
        List<String> comments = reviews.stream().map(Review::getComment).collect(Collectors.toList());
        List<LocalDate> dates = reviews.stream().map(Review::getTimestamp).collect(Collectors.toList());

        for (Review r : reviews) {
            double base = baseMap.get(r);
            double difficultyW = DifficultyModule.weight(r.getDifficulty());
            double months = TimeDecayModule.monthsBetween(r.getTimestamp(), now);
            double decayW = TimeDecayModule.decay(cfg.decayLambda, months);

            // temporal spike check — count reviews within windowDays of this review's date
            int countInWindow = 0;
            for (LocalDate d : dates) {
                double days = Math.abs(java.time.temporal.ChronoUnit.DAYS.between(d, r.getTimestamp()));
                if (days <= cfg.spikeWindowDays) countInWindow++;
            }
            boolean spike = AnomalyDetector.temporalSpike(countInWindow, cfg.spikeThreshold);

            // duplicate check (exclude same review by id)
            List<String> otherComments = reviews.stream().filter(rr -> !rr.getId().equals(r.getId()))
                    .map(Review::getComment).collect(Collectors.toList());
            boolean dup = AnomalyDetector.duplicatePattern(r.getComment(), otherComments, 0.8);

            boolean suspicious = spike || dup;

            double finalWeight = difficultyW * decayW * (suspicious ? cfg.suspiciousPenalty : 1.0);

            out.add(new AdjustedReview(r, base, finalWeight, suspicious));
        }
        return out;
    }
}
