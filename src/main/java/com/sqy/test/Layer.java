package com.sqy.test;

import java.util.*;

import com.sqy.activasion.SigmoidActivationFunctionFunction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
            neurons.add(new Neuron(inputSize, new SigmoidActivationFunctionFunction()));
        }
    }

    public double[] forward(double[] inputs) {
        outputs = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).activate(inputs);
        }
        return outputs;
    }

    public void computeOutputDeltas(int targetLabel) {
        for (int i = 0; i < neurons.size(); i++) {
            double target = (i == targetLabel) ? 1.0 : 0.0;
            neurons.get(i).computeOutputDelta(target);
        }
    }

    public void computeHiddenDeltas(Layer nextLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).computeHiddenDelta(nextLayer.getNeurons(), i);
        }
    }

    public void updateWeights(double[] inputs, double learningRate) {
        neurons.forEach(neuron -> neuron.updateWeights(inputs, learningRate));
    }
}
