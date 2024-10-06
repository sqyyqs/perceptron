package com.sqy;

import java.io.*;
import java.util.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.sqy.test.InputData;

public class MatrixCsvLoader {

    //todo add mapping
    private String filePath;
    private int classesNum;
    private int featuresNum;

    public MatrixCsvLoader(final int classesNum, final int featureNum, final String filePath) {
        this.featuresNum = featureNum;
        this.classesNum = classesNum;
        this.filePath = filePath;
    }

    public List<InputData> loadData() {
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(parser).build();

            reader.skip(1);
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                double[] array = Arrays.stream(nextLine)
                    .limit(nextLine.length - 2)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

                String lastElement = nextLine[nextLine.length - 1];


                double[] features = new double[featuresNum];
                for (int i = 0; i < featuresNum; i++) {
                    features[i] = Double.parseDouble(nextLine[i]);
                }
                // featuresList.add(features);
                // labels[featuresList.size() - 1] = nextLine[featuresNum - 1].charAt(0);
            }

            reader.close();

            // double[][] featureArray = featuresList.toArray(new double[0][0]);
            // return new DataSet(featureArray, labels);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
