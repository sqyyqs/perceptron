package com.sqy.loss;

@FunctionalInterface
public interface LossFunction {
    double calculateLoss(double[] predicted, int targetLabel);
}
