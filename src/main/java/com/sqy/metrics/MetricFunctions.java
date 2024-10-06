package com.sqy.metrics;

public class MetricFunctions {
    public static double recall(int tp, int fn) {
       return (double) tp / (tp + fn);
    }

    public static double precision(int tp, int fp) {
       return (double) tp / (tp + fp);
    }

    public static double accuracy(int tp, int fp, int fn, int tn) {
       return (double) (tp + tn) / (tp + tn + fn + fp);
    }
}

