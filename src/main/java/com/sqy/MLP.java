package com.sqy;

import java.util.*;

public class MLP {
    private int inputSize;
    private List<Integer> hiddenSizes;
    private int outputSize;
    private double learningRate;
    private String activationFunction;
    private String lossFunction;
    private int weightUpdateOption;
    private List<double[][]> weights;
    private List<double[]> biases;
    private List<double[][]> weightVelocities;
    private List<double[]> biasVelocities;
    private double momentum = 0.9;
    private List<double[][]> layerInputs;  // Z
    private List<double[][]> layerOutputs; // A

    public MLP(int inputSize, List<Integer> hiddenSizes, int outputSize, double learningRate,
               String activationFunction, String lossFunction, int weightUpdateOption) {
        this.inputSize = inputSize;
        this.hiddenSizes = hiddenSizes;
        this.outputSize = outputSize;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction.toLowerCase();
        this.lossFunction = lossFunction.toLowerCase();
        this.weightUpdateOption = weightUpdateOption;

        // initializeWeightsAndBiases();
    }
}
    //
    // private void initializeWeightsAndBiases() {
    //     weights = new ArrayList<>();
    //     biases = new ArrayList<>();
    //     layerInputs = new ArrayList<>();
    //     layerOutputs = new ArrayList<>();
    //
    //     int previousSize = inputSize;
    //     for (int size : hiddenSizes) {
    //         weights.add(MatrixUtils.randomMatrix(previousSize, size, -0.01, 0.01));
    //         biases.add(new double[size]);
    //         previousSize = size;
    //     }
    //
    //     weights.add(MatrixUtils.randomMatrix(previousSize, outputSize, -0.01, 0.01));
    //     biases.add(new double[outputSize]);
    //
    //     // Initialize velocities for momentum
    //     if (weightUpdateOption == 2) {
    //         weightVelocities = new ArrayList<>();
    //         biasVelocities = new ArrayList<>();
    //         for (int i = 0; i < weights.size(); i++) {
    //             weightVelocities.add(MatrixUtils.zeros(weights.get(i).length, weights.get(i)[0].length));
    //             biasVelocities.add(new double[biases.get(i).length]);
    //         }
    //     }
    // }
    //
    // public double[][] forward(double[][] input) {
    //     double[][] activation = input;
    //     layerInputs.clear();
    //     layerOutputs.clear();
    //
    //     for (int i = 0; i < hiddenSizes.size(); i++) {
    //         double[][] z = MatrixUtils.add(MatrixUtils.multiply(activation, weights.get(i)), biases.get(i));
    //         layerInputs.add(z);
    //         activation = Activation.sigmoid(z);
    //         layerOutputs.add(activation);
    //     }
    //
    //     // Forward through output layer
    //     double[][] z = MatrixUtils.add(MatrixUtils.multiply(activation, weights.getLast()), biases.getLast());
    //     layerInputs.add(z);
    //
    //     activation = Activation.crossEntropyLoss()
    //     layerOutputs.add(activation);
    //
    //     return activation;
    // }
    //
    // Backward pass
//     public void backward(double[][] input, double[][] labels, double[][] predictions) {
//         int numLayers = weights.size();
//         int batchSize = input.length;
//
//         // Initialize gradients
//         List<double[][]> weightGradients = new ArrayList<>();
//         List<double[]> biasGradients = new ArrayList<>();
//         for (int i = 0; i < numLayers; i++) {
//             weightGradients.add(new double[weights.get(i).length][weights.get(i)[0].length]);
//             biasGradients.add(new double[biases.get(i).length]);
//         }
//
//         // Calculate delta for output layer
//         double[][] delta = new double[predictions.length][predictions[0].length];
//         for (int i = 0; i < predictions.length; i++) {
//             for (int j = 0; j < predictions[0].length; j++) {
//                 delta[i][j] = predictions[i][j] - labels[i][j]; // dL/dZ
//             }
//         }
//     //
//     //     // Iterate backward through layers
//     //     for (int l = numLayers - 1; l >= 0; l--) {
//     //         double[][] activationPrev;
//     //         if (l == 0) {
//     //             activationPrev = input;
//     //         } else {
//     //             activationPrev = layerOutputs.get(l - 1);
//     //         }
//     //
//     //         // Gradient for weights and biases
//     //         double[][] activationPrevT = Matrix.transpose(activationPrev);
//     //         double[][] dW = Matrix.multiply(activationPrevT, delta);
//     //         double[] db = Matrix.sumColumns(delta);
//     //
//     //         // Average gradients over batch
//     //         for (int i = 0; i < dW.length; i++) {
//     //             for (int j = 0; j < dW[0].length; j++) {
//     //                 dW[i][j] /= batchSize;
//     //             }
//     //         }
//     //         for (int j = 0; j < db.length; j++) {
//     //             db[j] /= batchSize;
//     //         }
//     //
//     //         weightGradients.set(l, dW);
//     //         biasGradients.set(l, db);
//     //
//     //         // If not the first layer, propagate the delta
//     //         if (l > 0) {
//     //             double[][] weightsCurrentT = Matrix.transpose(weights.get(l));
//     //             double[][] deltaPrev = Matrix.multiply(delta, weightsCurrentT);
//     //
//     //             // Apply derivative of activation function
//     //             double[][] activationDeriv;
//     //             if (activationFunction.equals("relu")) {
//     //                 activationDeriv = Activation.reluDerivative(layerInputs.get(l - 1));
//     //             } else if (activationFunction.equals("sigmoid")) {
//     //                 activationDeriv = Activation.applyDerivative(layerInputs.get(l - 1));
//     //             } else {
//     //                 throw new IllegalArgumentException("Unsupported activation function: " + activationFunction);
//     //             }
//     //
//     //             delta = Matrix.multiplyElements(deltaPrev, activationDeriv);
//     //         }
//     //     }
//     //
//     //     // Update weights and biases
//     //     for (int l = 0; l < numLayers; l++) {
//     //         if (weightUpdateOption == 1) { // Gradient Descent
//     //             // Update weights
//     //             for (int i = 0; i < weights.get(l).length; i++) {
//     //                 for (int j = 0; j < weights.get(l)[0].length; j++) {
//     //                     weights.get(l)[i][j] -= learningRate * weightGradients.get(l)[i][j];
//     //                 }
//     //             }
//     //
//     //             // Update biases
//     //             for (int j = 0; j < biases.get(l).length; j++) {
//     //                 biases.get(l)[j] -= learningRate * biasGradients.get(l)[j];
//     //             }
//     //         } else if (weightUpdateOption == 2) { // Momentum-based Gradient Descent
//     //             // Update weight velocities and weights
//     //             for (int i = 0; i < weights.get(l).length; i++) {
//     //                 for (int j = 0; j < weights.get(l)[0].length; j++) {
//     //                     weightVelocities.get(l)[i][j] = momentum * weightVelocities.get(l)[i][j] - learningRate * weightGradients.get(l)[i][j];
//     //                     weights.get(l)[i][j] += weightVelocities.get(l)[i][j];
//     //                 }
//     //             }
//     //
//     //             // Update bias velocities and biases
//     //             for (int j = 0; j < biases.get(l).length; j++) {
//     //                 biasVelocities.get(l)[j] = momentum * biasVelocities.get(l)[j] - learningRate * biasGradients.get(l)[j];
//     //                 biases.get(l)[j] += biasVelocities.get(l)[j];
//     //             }
//     //         } else {
//     //             throw new IllegalArgumentException("Unsupported weight update option: " + weightUpdateOption);
//     //         }
//     //     }
//     // }
//     //
//     // // Predict class labels
//     // public int[] predict(double[][] input) {
//     //     double[][] probabilities = forward(input);
//     //     int[] predictions = new int[input.length];
//     //
//     //     for (int i = 0; i < probabilities.length; i++) {
//     //         double maxProb = probabilities[i][0];
//     //         int maxIndex = 0;
//     //         for (int j = 1; j < probabilities[i].length; j++) {
//     //             if (probabilities[i][j] > maxProb) {
//     //                 maxProb = probabilities[i][j];
//     //                 maxIndex = j;
//     //             }
//     //         }
//     //         predictions[i] = maxIndex;
//     //     }
//     //
//     //     return predictions;
//     // }
// }
