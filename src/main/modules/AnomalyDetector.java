package com.example.jobsuccess.modules;

import java.util.List;

public class AnomalyDetector {

    public static boolean duplicatePattern(String comment, List<String> otherComments, double jaccardThreshold) {
        if (comment == null || comment.isBlank()) return false;
        for (String other : otherComments) {
            if (other == null) continue;
            if (comment.equalsIgnoreCase(other)) return true;
            // simple token overlap jaccard (naive)
            String[] a = comment.toLowerCase().split("\\s+");
            String[] b = other.toLowerCase().split("\\s+");
            java.util.Set<String> sa = new java.util.HashSet<>(java.util.Arrays.asList(a));
            java.util.Set<String> sb = new java.util.HashSet<>(java.util.Arrays.asList(b));
            if (!sa.isEmpty() && !sb.isEmpty()) {
                java.util.Set<String> inter = new java.util.HashSet<>(sa);
                inter.retainAll(sb);
                java.util.Set<String> union = new java.util.HashSet<>(sa);
                union.addAll(sb);
                double j = (double) inter.size() / (double) union.size();
                if (j >= jaccardThreshold) return true;
            }
        }
        return false;
    }

    public static boolean temporalSpike(int countInWindow, int threshold) {
        return countInWindow > threshold;
    }
}
