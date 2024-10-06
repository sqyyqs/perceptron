package com.sqy.loss;

public class CrossEntropyLossFunction implements LossFunction {
    private static final double EPSILON = 1e-15;

    @Override
    public double calculateLoss(final double[] predicted, final int targetLabel) {
        double totalLoss = 0.0;
        for (int i = 0; i < predicted.length; i++) {
            double value = (i == targetLabel) ? 1.0 : 0.0;
            double log = Math.log(predicted[i] + EPSILON);
            totalLoss -= (value * log);
        }
        return totalLoss;
    }
}
