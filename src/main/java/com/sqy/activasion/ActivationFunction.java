package com.sqy.activasion;

public interface ActivationFunction {
    double apply(double value);
    double applyDerivative(double value);

    default double crossEntropyLoss(double[][] predictions, double[][] labels) {
        int rows = predictions.length;
        int cols = predictions[0].length;
        double loss = 0.0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (labels[i][j] == 1.0) {
                    loss -= Math.log(predictions[i][j] + 1e-15);
                }
            }
        }

        return loss / rows;
    }
}
