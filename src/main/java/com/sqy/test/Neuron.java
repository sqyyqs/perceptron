package com.sqy.test;

import java.util.*;

public class Neuron {
    private int inputSize;
    private double[] weights; // weights[input]
    private double bias;
    private double output;     // Output after activation
    private double inputSum;   // Weighted sum before activation
    private double delta;      // Error term for backpropagation

    private static final Random rand = new Random();

    public Neuron(int inputSize) {
        this.inputSize = inputSize;
        initializeWeightsAndBias();
    }

    private void initializeWeightsAndBias() {
        weights = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            weights[i] = rand.nextGaussian() * 0.01;
        }
        bias = 0.0;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1.0 - x);
    }

    public double forward(double[] inputs) {
        inputSum = 0.0;
        for (int i = 0; i < inputSize; i++) {
            inputSum += weights[i] * inputs[i];
        }
        inputSum += bias;
        output = sigmoid(inputSum);
        return output;
    }

    public void computeOutputDelta(double target) {
        delta = output - target;
    }

    public void computeHiddenDelta(double[] nextLayerWeights, double[] nextLayerDeltas) {
        double sum = 0.0;
        for (int i = 0; i < nextLayerDeltas.length; i++) {
            sum += nextLayerWeights[i] * nextLayerDeltas[i];
        }
        delta = sum * sigmoidDerivative(output);
    }

    public void updateWeights(double[] inputs, double learningRate) {
        for (int i = 0; i < inputSize; i++) {
            weights[i] -= learningRate * delta * inputs[i];
        }
        bias -= learningRate * delta;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(final int inputSize) {
        this.inputSize = inputSize;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(final double[] weights) {
        this.weights = weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(final double bias) {
        this.bias = bias;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(final double output) {
        this.output = output;
    }

    public double getInputSum() {
        return inputSum;
    }

    public void setInputSum(final double inputSum) {
        this.inputSum = inputSum;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(final double delta) {
        this.delta = delta;
    }
}
