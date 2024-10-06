package com.sqy.core;

import java.util.*;

import com.sqy.domain.ClassLabelMapping;
import com.sqy.domain.InputData;
import com.sqy.loss.LossFunction;

public class MultiLayerPerceptron {
    private final List<Layer> layers = new ArrayList<>();
    private final double learningRate;
    private final LossFunction lossFunction;

    public MultiLayerPerceptron(int inputSize,
                                int[] hiddenLayerSizes,
                                int outputSize,
                                double learningRate,
                                LossFunction lossFunction) {
        this.learningRate = learningRate;
        this.lossFunction = lossFunction;

        int previousSize = inputSize;
        for (int size : hiddenLayerSizes) {
            layers.add(new Layer(previousSize, size));
            previousSize = size;
        }

        layers.add(new Layer(previousSize, outputSize));
    }

    public double[] forward(double[] inputs) {
        double[] output = inputs;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    public void backpropagate(int targetLabel, double[] inputs) {
        layers.getLast().computeOutputDeltas(targetLabel);

        for (int l = layers.size() - 2; l >= 0; l--) {
            Layer currentLayer = layers.get(l);
            Layer nextLayer = layers.get(l + 1);
            currentLayer.computeHiddenDeltas(nextLayer);
        }

        double[] layerInputs = inputs;
        for (Layer layer : layers) {
            layer.updateWeights(layerInputs, learningRate);
            layerInputs = layer.getOutputs();
        }
    }

    public void train(List<InputData> dataset, int epochs) {
        for (int epoch = 1; epoch <= epochs; epoch++) {
            double totalLoss = 0.0;
            Collections.shuffle(dataset);
            for (InputData inputData : dataset) {
                double[] inputs = inputData.data();
                forward(inputs);
                backpropagate(ClassLabelMapping.from(inputData), inputs);
                totalLoss += computeLoss(ClassLabelMapping.from(inputData));
            }
            System.out.println("Epoch " + epoch + " - Loss: " + (totalLoss / dataset.size()));
        }
    }

    private double computeLoss(int targetLabel) {
        Layer outputLayer = layers.getLast();
        double[] predicted = outputLayer.getOutputs();
        return lossFunction.calculateLoss(predicted, targetLabel);
    }

    public int predict(double[] inputs) {
        double[] outputs = forward(inputs);
        int predictedLabel = 0;
        double max = Double.MIN_VALUE;
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > max) {
                max = outputs[i];
                predictedLabel = i;
            }
        }
        return predictedLabel;
    }
}
