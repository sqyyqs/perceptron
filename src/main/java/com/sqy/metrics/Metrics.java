package com.sqy.metrics;

public record Metrics(
    double accuracy,
    double precision,
    double recall,
    double lossFunctionValue
) {
    public String formatMetrics() {
        return "Metrics{accuracy=%.2f, precision=%.2f , recall=%.2f , lossFunctionValue=%.2f}"
            .formatted(accuracy, precision, recall, lossFunctionValue);
    }
}
