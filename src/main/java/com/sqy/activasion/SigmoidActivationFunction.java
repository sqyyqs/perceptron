package com.sqy.activasion;

import com.sqy.MatrixUtils;

public class SigmoidActivationFunction implements Activation {
    @Override
    public double[][] apply(final double[][] x) {
        return MatrixUtils.applyFunction(x, val -> 1.0 / (1.0 + Math.exp(-val)));
    }

    @Override
    public double[][] applyDerivative(double[][] sigmoidOutput) {
        return MatrixUtils.multiplyElements(sigmoidOutput, MatrixUtils.subtract(sigmoidOutput, MatrixUtils.applyFunction(sigmoidOutput, val -> 0.0)));
    }
}
