package com.sqy;

import java.util.*;

import com.sqy.configuration.MultiLayerPerceptronConfiguration;
import com.sqy.core.MultiLayerPerceptron;
import com.sqy.domain.ClassLabelMapping;
import com.sqy.domain.InputData;
import com.sqy.metrics.Metrics;
import com.sqy.util.MatrixCsvLoader;

public class MultiLayerPerceptronRunner {
    private final int inputSize = 32 * 32;
    private final int[] hiddenLayerSizes = MultiLayerPerceptronConfiguration.HIDDEN_LAYER_SIZES;
    private final int outputSize = 10;
    private final double learningRate = 0.2;
    private final MultiLayerPerceptron mlp = new MultiLayerPerceptron(inputSize, hiddenLayerSizes, outputSize, learningRate);

    public List<Metrics> train(int epochs) {
        List<InputData> dataSet = loadTrainingInputs();
        return mlp.train(dataSet, epochs);
    }

    public char predict(double[] data) {
        int predict = mlp.predict(data);
        return ClassLabelMapping.from(predict);
    }

    public double test() {
        List<InputData> testData = loadTestingInputs();
        int correctCount = 0;
        int totalCount = 0;
        for (InputData testDataElement : testData) {
            totalCount++;
            if (ClassLabelMapping.from(mlp.predict(testDataElement.data())) == testDataElement.label()) {
                correctCount++;
            }
        }
        return (double) correctCount / totalCount;
    }

    private static List<InputData> loadTrainingInputs() {
        MatrixCsvLoader matrixCsvLoader = new MatrixCsvLoader("/Users/konstantindemin/ucheba/3kurs/intellectual_systems/lr2/src/main/resources/new_output.csv");
        return matrixCsvLoader.loadData();
    }

    private static List<InputData> loadTestingInputs() {
        MatrixCsvLoader matrixCsvLoader = new MatrixCsvLoader("/Users/konstantindemin/ucheba/3kurs/intellectual_systems/lr2/src/main/resources/deduction.csv");
        return matrixCsvLoader.loadData();
    }
}
