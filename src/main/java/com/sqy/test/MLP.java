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
        // Forward pass
        double[] outputs = forward(inputs);

        List<double[]> deltas = new ArrayList<>();

        // Compute delta for output layer (assuming softmax and cross-entropy loss)
        // For simplicity, we'll use sigmoid activation here as per previous implementation
        // Modify accordingly if using softmax

        Layer outputLayer = layers.getLast();
        double[] outputDeltas = new double[outputLayer.getOutputSize()];
        for (int i = 0; i < outputLayer.getOutputSize(); i++) {
            double target = (i == targetLabel) ? 1.0 : 0.0;
            // Assuming sigmoid activation
            outputDeltas[i] = outputs[i] - target;
        }
        deltas.add(outputDeltas);

        // Compute deltas for hidden layers
        for (int l = layers.size() - 2; l >= 0; l--) {
            Layer currentLayer = layers.get(l);
            Layer nextLayer = layers.get(l + 1);
            double[] currentDeltas = new double[currentLayer.getOutputSize()];

            for (int i = 0; i < currentLayer.getOutputSize(); i++) {
                double sum = 0.0;
                for (int j = 0; j < nextLayer.getOutputSize(); j++) {
                    Neuron nextNeuron = nextLayer.getNeurons().get(j);
                    sum += nextNeuron.getWeights()[i] * deltas.getFirst()[j];
                }
                // Assuming sigmoid activation
                double output = currentLayer.getNeurons().get(i).getOutput();
                currentDeltas[i] = sum * output * (1 - output);
            }
            deltas.addFirst(currentDeltas); // Insert at the beginning
        }

        // Update weights and biases for all layers
        double[] layerInput = inputs;
        for (int l = 0; l < layers.size(); l++) {
            Layer layer = layers.get(l);
            double[] layerDeltas = deltas.get(l);
            // Update each neuron in the layer
            for (int n = 0; n < layer.getOutputSize(); n++) {
                Neuron neuron = layer.getNeurons().get(n);
                // Update weights
                for (int w = 0; w < neuron.getWeights().length; w++) {
                    neuron.getWeights()[w] -= learningRate * layerDeltas[n] * layerInput[w];
                }
                // Update bias
                neuron.setBias(neuron.getBias() - learningRate * layerDeltas[n]);
            }
            // Set input for next layer
            layerInput = layer.getOutputs();
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
