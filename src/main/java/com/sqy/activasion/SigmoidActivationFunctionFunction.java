package com.sqy.activasion;

public class SigmoidActivationFunctionFunction implements ActivationFunction {
    @Override
    public double apply(final double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }

    @Override
    public double applyDerivative(final double value) {
        return value * (1 - value);
    }
}
