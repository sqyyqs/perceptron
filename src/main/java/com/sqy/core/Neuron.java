package com.sqy.core;

import java.util.*;

import com.sqy.activasion.ActivationFunction;
import com.sqy.configuration.MultiLayerPerceptronConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Neuron {
    private int inputSize;
    private double[] weights;
    private double bias;
    private double output;
    private double inputSum;
    private double delta;
    private final ActivationFunction activationFunction;
    private static final Random rand = new Random();

    public Neuron(int inputSize, final ActivationFunction activationFunction) {
        this.inputSize = inputSize;
        this.activationFunction = activationFunction;
        initializeWeightsAndBias();
    }

    public Neuron(int inputSize) {
        this(inputSize, MultiLayerPerceptronConfiguration.defaultActivationFunction);
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
        output = activationFunction.apply(z);
        return output;
    }

    public void computeOutputDelta(double target) {
        delta = output - target;
    }

    public void computeHiddenDelta(List<Neuron> nextLayerNeurons, int index) {
        double sum = 0.0;
        for (Neuron neuron : nextLayerNeurons) {
            sum += neuron.getWeights()[index] * neuron.getDelta();
        }
        delta = sum * activationFunction.applyDerivative(output);
    }

    public void updateWeights(double[] inputs, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] -= learningRate * delta * inputs[i];
        }
        bias -= learningRate * delta;
    }
}
