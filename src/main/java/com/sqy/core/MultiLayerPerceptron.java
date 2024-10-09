package com.sqy.core;

import java.util.*;

import com.sqy.configuration.MultiLayerPerceptronConfiguration;
import com.sqy.domain.ClassLabelMapping;
import com.sqy.domain.InputData;
import com.sqy.loss.LossFunction;
import com.sqy.metrics.Metrics;
import com.sqy.metrics.MetricsBuilder;

public class MultiLayerPerceptron {
    private final List<Layer> layers = new ArrayList<>();
    private final double learningRate;
    private final LossFunction lossFunction;

    public MultiLayerPerceptron(int inputSize, int[] hiddenLayerSizes, int outputSize,
                                double learningRate, LossFunction lossFunction) {
        this.learningRate = learningRate;
        this.lossFunction = lossFunction;

        int previousSize = inputSize;
        for (int size : hiddenLayerSizes) {
            layers.add(new Layer(previousSize, size));
            previousSize = size;
        }

        layers.add(new Layer(previousSize, outputSize));
    }

    public MultiLayerPerceptron(int inputSize, int[] hiddenLayerSizes, int outputSize, double learningRate) {
        this(inputSize, hiddenLayerSizes, outputSize, learningRate, MultiLayerPerceptronConfiguration.defaultLossFunction);
    }

    public double[] forward(double[] inputs) {
        double[] output = inputs;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    public void backpropagation(int targetLabel, double[] inputs) {
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

    public List<Metrics> train(List<InputData> dataset, int epochs) {
        MetricsBuilder metricsBuilder = new MetricsBuilder();
        List<Metrics> metricsResult = new ArrayList<>();
        for (int epoch = 1; epoch <= epochs; epoch++) {
            double totalLoss = 0.0;
            Collections.shuffle(dataset);
            for (InputData inputData : dataset) {
                double[] inputs = inputData.data();
                forward(inputs);
                backpropagation(ClassLabelMapping.from(inputData), inputs);
                metricsBuilder.addValue(layers.getLast().getOutputs(), ClassLabelMapping.from(inputData));

                totalLoss += computeLoss(ClassLabelMapping.from(inputData));
            }
            Metrics metrics = metricsBuilder.addLossFunctionValue(totalLoss / dataset.size()).createMetrics();
            metricsResult.add(metrics);
            metricsBuilder.clear();
        }
        return metricsResult;
    }

    private double computeLoss(int targetLabel) {
        Layer outputLayer = layers.getLast();
        double[] predicted = outputLayer.getOutputs();
        return lossFunction.calculateLoss(predicted, targetLabel);
    }

    public int predict(double[] inputs) {
        return maxIdx(forward(inputs));
    }

    private static int maxIdx(double[] arr) {
        int predictedLabel = 0;
        double max = Double.MIN_VALUE;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                predictedLabel = i;
            }
        }
        return predictedLabel;
    }
}
