package com.example.jobsuccess;

public class Config {
    public final double trimPercent = 0.10;
    public final double bayesAlpha = 10.0;
    public final double bayesMu = 4.5;
    public final double stretchFactor = 1.10;
    public final double suspiciousPenalty = 0.5;
    public final double decayLambda = 0.05; // per month
    public final int spikeWindowDays = 1;
    public final int spikeThreshold = 5;
}
