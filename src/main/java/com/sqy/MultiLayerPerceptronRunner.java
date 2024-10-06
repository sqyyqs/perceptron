package com.sqy;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.sqy.configuration.MultiLayerPerceptronConfiguration;
import com.sqy.core.MultiLayerPerceptron;
import com.sqy.metrics.MetricsBuilder;
import com.sqy.util.MatrixCsvLoader;
import com.sqy.domain.ClassLabelMapping;
import com.sqy.domain.InputData;

public class MultiLayerPerceptronRunner {
    public static void main(String[] args) {
        int inputSize = 32 * 32;
        int[] hiddenLayerSizes = { 128, 64 };
        int outputSize = 10;
        double learningRate = 0.2;

        MultiLayerPerceptron mlp = new MultiLayerPerceptron(inputSize, hiddenLayerSizes, outputSize, learningRate);

        List<InputData> dataSet = loadTrainingInputs();
        mlp.train(dataSet);

        InputData inputData = dataSet.get(ThreadLocalRandom.current().nextInt(dataSet.size()));

        int predict = mlp.predict(inputData.data());

        System.out.println(ClassLabelMapping.from(inputData));
        System.out.println(predict);
    }

    private static List<InputData> loadTrainingInputs() {
        MatrixCsvLoader matrixCsvLoader = new MatrixCsvLoader("/Users/konstantindemin/Downloads/ier/ier/new_output.csv");
        return matrixCsvLoader.loadData();
    }

    private static int[] loadTrainingLabels() {
        //тут  будет загружаться тестовая выборка
        return new int[0];
    }
}
