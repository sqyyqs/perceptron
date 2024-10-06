package com.sqy;

import java.util.function.Function;

public class MatrixUtils {
    public static double[][] multiply(double[][] a, double[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bCols = b[0].length;

        double[][] result = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    public static double[][] add(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        return result;
    }

    public static double[][] add(double[][] a, double[] b) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[j];
            }
        }

        return result;
    }

    public static double[][] subtract(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }

        return result;
    }

    public static double[][] transpose(double[][] a) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = a[i][j];
            }
        }

        return result;
    }

    public static double[][] applyFunction(double[][] a, Function<Double, Double> func) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = func.apply(a[i][j]);
            }
        }

        return result;
    }

    public static double[][] multiplyElements(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] * b[i][j];
            }
        }

        return result;
    }

    public static double[][] randomMatrix(int rows, int cols, double min, double max) {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = min + Math.random() * (max - min);
            }
        }
        return result;
    }

    public static void printMatrix(double[][] a) {
        for (double[] row : a) {
            for (double val : row) {
                System.out.printf("%.4f ", val);
            }
            System.out.println();
        }
    }
}
