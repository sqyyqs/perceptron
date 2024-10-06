package com.sqy.util;

import java.io.*;
import java.util.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sqy.domain.InputData;

public class MatrixCsvLoader {
    private String filePath;

    public MatrixCsvLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<InputData> loadData() {
        List<InputData> result = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(parser).build()) {
            reader.skip(1);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                double[] data = Arrays.stream(nextLine)
                    .limit(nextLine.length - 2)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

                char label = nextLine[nextLine.length - 1].charAt(0);
                result.add(new InputData(data, label));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
