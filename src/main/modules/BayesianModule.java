package com.example.jobsuccess.modules;

public class BayesianModule {
    public static double apply(double mean, int n, double alpha, double mu) {
        if (n <= 0) return mu;
        return (alpha * mu + n * mean) / (alpha + n);
    }
}

