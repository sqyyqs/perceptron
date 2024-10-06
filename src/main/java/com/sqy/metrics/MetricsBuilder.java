package com.sqy.metrics;

public class MetricsBuilder {
    private int truePositive = 0;
    private int trueNegative = 0;
    private int falsePositive = 0;
    private int falseNegative = 0;
    private double lossFunctionValue;

    public MetricsBuilder addValue(double[] predicted, int targetLabel) {
        for (int i = 0; i < predicted.length; i++) {
            double predictedValue = predicted[i];
            if (predictedValue > 0.5) {
                if (i == targetLabel) {
                    truePositive++;
                } else {
                    falsePositive++;
                }
            } else {
                if (i != targetLabel) {
                    trueNegative++;
                } else {
                    falseNegative++;
                }
            }
        }
        return this;
    }

    public MetricsBuilder addLossFunctionValue(double value) {
        lossFunctionValue = value;
        return this;
    }

    public MetricsBuilder clear() {
        trueNegative = 0;
        truePositive = 0;
        falseNegative = 0;
        falsePositive = 0;
        return this;
    }

    public Metrics createMetrics() {
        double recall = MetricFunctions.recall(truePositive, falseNegative);
        double accuracy = MetricFunctions.accuracy(truePositive, falsePositive, falseNegative, trueNegative);
        double precision = MetricFunctions.precision(truePositive, falsePositive);

        return new Metrics(accuracy, precision, recall, lossFunctionValue);
    }

}
