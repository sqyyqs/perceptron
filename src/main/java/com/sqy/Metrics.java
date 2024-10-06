package com.sqy;

public class Metrics {
    public static double accuracy(int[] predictions, int[] actual) {
        if (predictions.length != actual.length) {
            throw new IllegalArgumentException("Length of predictions and actual labels must be equal.");
        }

        int correct = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == actual[i]) {
                correct++;
            }
        }

        return (double) correct / predictions.length;
    }

    public static double precision(int[] predictions, int[] actual, int numClasses) {
        double[] tp = new double[numClasses];
        double[] fp = new double[numClasses];

        for (int i = 0; i < predictions.length; i++) {
            int pred = predictions[i];
            int act = actual[i];
            if (pred == act) {
                tp[pred]++;
            } else {
                fp[pred]++;
            }
        }

        double precisionSum = 0.0;
        for (int c = 0; c < numClasses; c++) {
            if (tp[c] + fp[c] == 0) {
                precisionSum += 0.0;
            } else {
                precisionSum += tp[c] / (tp[c] + fp[c]);
            }
        }

        return precisionSum / numClasses;
    }

    public static double recall(int[] predictions, int[] actual, int numClasses) {
        double[] tp = new double[numClasses];
        double[] fn = new double[numClasses];

        for (int i = 0; i < predictions.length; i++) {
            int pred = predictions[i];
            int act = actual[i];
            if (pred == act) {
                tp[act]++;
            } else {
                fn[act]++;
            }
        }

        double recallSum = 0.0;
        for (int c = 0; c < numClasses; c++) {
            if (tp[c] + fn[c] == 0) {
                recallSum += 0.0;
            } else {
                recallSum += tp[c] / (tp[c] + fn[c]);
            }
        }

        return recallSum / numClasses;
    }
}

