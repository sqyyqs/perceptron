package com.sqy.test;

import java.util.*;

public class Neuron {
    private int inputSize;
    private double[] weights;
    private double bias;
    private double output;
    private double inputSum;
    private double delta;

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

    public double activate(double[] inputs) {
        double z = bias;
        for (int i = 0; i < weights.length; i++) {
            z += weights[i] * inputs[i];
        }
        output = sigmoid(z);
        return output;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double sigmoidDerivative() {
        return output * (1 - output);
    }

    public void computeOutputDelta(double target) {
        delta = output - target;
    }

    public void computeHiddenDelta(List<Neuron> nextLayerNeurons, int index) {
        double sum = 0.0;
        for (Neuron neuron : nextLayerNeurons) {
            sum += neuron.getWeights()[index] * neuron.getDelta();
        }
        delta = sum * sigmoidDerivative();
    }

    public void updateWeights(double[] inputs, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
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
