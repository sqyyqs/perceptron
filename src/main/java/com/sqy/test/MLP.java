package com.sqy.test;

import java.util.*;

public class MLP {
    private final List<Layer> layers = new ArrayList<>();
    private final double learningRate;

    public MLP(int inputSize, int[] hiddenLayerSizes, int outputSize, double learningRate) {
        this.learningRate = learningRate;

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

    public double[] backpropagate(double[] inputs, int targetLabel) {
        double[] outputs = forward(inputs);

        layers.getLast().computeOutputDeltas(targetLabel);

        // Compute deltas for hidden layers in reverse order
        for (int l = layers.size() - 2; l >= 0; l--) {
            Layer currentLayer = layers.get(l);
            Layer nextLayer = layers.get(l + 1);
            currentLayer.computeHiddenDeltas(nextLayer);
        }

        // Update weights and biases layer by layer
        double[] layerInputs = inputs;
        for (Layer layer : layers) {
            layer.updateWeights(layerInputs, learningRate);
            layerInputs = layer.getOutputs();
        }

        return outputs;
    }

    public void train(List<InputData> dataset, int epochs) {
        for (int epoch = 1; epoch < epochs; epoch++) {
            double totalLoss = 0.0;
            Collections.shuffle(dataset);
            for (InputData inputData : dataset) {
                double[] backpropagationData = backpropagate( inputData.data(), ClassLabelMapping.from(inputData));
                totalLoss += computeLoss(backpropagationData, ClassLabelMapping.from(inputData));
            }
            System.out.println("Epoch " + epoch + " - Loss: " + (totalLoss / dataset.size()));
        }
    }

    private double computeLoss(double[] predicted, int targetLabel) {
        double loss = 0.0;
        for (int i = 0; i < predicted.length; i++) {
            double target = (i == targetLabel) ? 1.0 : 0.0;
            // To prevent log(0)
            loss -= target * Math.log(predicted[i] + 1e-15) + (1 - target) * Math.log(1 - predicted[i] + 1e-15);
        }
        return loss;
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
        System.out.println(predictedLabel);
        return predictedLabel;
    }
}
