package com.sqy.metrics;

public record Metrics(
    double accuracy,
    double precision,
    double recall,
    double lossFunctionValue
) {
    public String formatMetrics() {
        return "Metrics{" + "accuracy=" + accuracy
               + ", precision=" + precision
               + ", recall=" + recall
               + ", lossFunctionValue=" + lossFunctionValue
               + '}';
    }
}
