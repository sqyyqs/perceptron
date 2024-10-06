package com.sqy;

import com.sqy.activasion.Activation;
import com.sqy.activasion.SigmoidActivationFunction;

public class Layer {

    private int inputSize;
    private int outputSize;
    private double[][] weights;
    private double[] biases;
    private double[][] input;
    private double[][] output;
    private double[][] delta;
    private Activation activation = new SigmoidActivationFunction();

    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;

        this.weights = MatrixUtils.randomMatrix(inputSize, outputSize, -0.01, 0.01);
        this.biases = new double[outputSize];
    }
    //
    // public double[][] forward(double[][] input) {
    //     this.input = input;
    //     double[][] z = MatrixUtils.add(MatrixUtils.multiply(input, weights), biases);
    //     this.output = activation.apply(z);
    //     return output;
    // }
    //
    // public void backward(double[][] upstreamGradient, double learningRate) {
    //     double[][] activationDerivative = activation.applyDerivative(output);
    //
    //     this.delta = MatrixUtils.multiplyElements(upstreamGradient, activationDerivative);
    //
    //     // Compute gradients
    //     double[][] gradWeights = Matrix.multiply(Matrix.transpose(input), delta);
    //     double[] gradBiases = Matrix.sumColumns(delta);
    //
    //     // Update weights and biases
    //     weights = Matrix.subtract(weights, Matrix.scalarMultiply(gradWeights, learningRate));
    //     biases = Matrix.subtract(biases, Matrix.scalarMultiply(gradBiases, learningRate));
    // }

    // Getters for weights and biases if needed
}
