package com.example.jobsuccess.modules;

public class VarianceStretchModule {
    public static double stretch(double score, double mu, double factor) {
        double res = mu + factor * (score - mu);
        return Math.max(0.0, Math.min(5.0, res));
    }
}
