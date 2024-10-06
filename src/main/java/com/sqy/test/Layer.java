package com.sqy.test;

import java.util.*;

public class Layer {
    private int inputSize;
    private int outputSize;
    private List<Neuron> neurons;
    private double[] outputs;

    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        neurons = new ArrayList<>();
        for (int i = 0; i < outputSize; i++) {
            neurons.add(new Neuron(inputSize));
        }
    }

    public double[] forward(double[] inputs) {
        outputs = new double[outputSize];
        for (int i = 0; i < outputSize; i++) {
            outputs[i] = neurons.get(i).forward(inputs);
        }
        return outputs;
    }

    public void updateWeights(double[] inputs, double learningRate) {
        for (Neuron neuron : neurons) {
            neuron.updateWeights(inputs, learningRate);
        }
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(final int inputSize) {
        this.inputSize = inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(final int outputSize) {
        this.outputSize = outputSize;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(final List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void setOutputs(final double[] outputs) {
        this.outputs = outputs;
    }
}
