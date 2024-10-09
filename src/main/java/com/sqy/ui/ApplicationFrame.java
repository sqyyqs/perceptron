package com.sqy.ui;

import java.util.*;
import java.util.function.DoubleSupplier;
import java.util.function.IntFunction;

import com.sqy.MultiLayerPerceptronRunner;
import com.sqy.metrics.Metrics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApplicationFrame extends Application {
    private static final int PIXELS = 16;
    private final MultiLayerPerceptronRunner multiLayerPerceptronRunner = new MultiLayerPerceptronRunner();
    private final TextArea textArea = new TextArea();
    private final PixelCanva pixelCanva = new PixelCanva(512, 512);
    private final Slider valueSlider = new Slider(5, 20, 10);
    private final Label sliderValueLabel = new Label("Количество эпох: " + (int) valueSlider.getValue());

    @Override
    public void start(Stage primaryStage) {
        pixelCanva.drawGrid(PIXELS, Color.LIGHTGRAY);

        valueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderValueLabel.setText("Value: " + newValue.intValue());
        });

        IntFunction<List<Metrics>> trainConsumer = multiLayerPerceptronRunner::train;
        DoubleSupplier testSupplier = multiLayerPerceptronRunner::test;

        BorderPane root = new BorderPane();
        StackPane canvasContainer = new StackPane(pixelCanva);

        valueSlider.setShowTickLabels(true);
        valueSlider.setShowTickMarks(true);
        valueSlider.setMajorTickUnit(5);
        valueSlider.setBlockIncrement(5);

        textArea.setPrefHeight(125);
        textArea.setEditable(false);

        root.setTop(topControls(trainConsumer, testSupplier));
        root.setCenter(canvasContainer);
        root.setBottom(textArea);
        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("перцептрончик на джаве");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private HBox topControls(IntFunction<List<Metrics>> intConsumer, DoubleSupplier secondButtonCallback) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(event -> pixelCanva.setCurrentColor(colorPicker.getValue()));

        Button transformButton = predictImageButton();
        Button testButton = testButton(secondButtonCallback);
        Button trainButton = trainButton(intConsumer);
        Button clearButton = clearButton();

        return new HBox(10, colorPicker, valueSlider, sliderValueLabel, trainButton, testButton, transformButton, clearButton);
    }

    private Button predictImageButton() {
        Button predictImageButton = new Button("Распознать изображение");
        predictImageButton.setOnAction(event -> {
            double[] canvasData = pixelCanva.transformCanvasToDoubleArray();
            char predict = multiLayerPerceptronRunner.predict(canvasData);
            textArea.setText(predict + "");
        });
        return predictImageButton;
    }

    private Button clearButton() {
        Button clearButton = new Button("Очистить");
        clearButton.setOnAction(event -> {
            pixelCanva.clearCanvas();
            pixelCanva.drawGrid(PIXELS, Color.LIGHTGRAY);
            textArea.clear();
        });
        return clearButton;
    }

    private Button trainButton(IntFunction<List<Metrics>> intConsumer) {
        Button trainButton = new Button("Тренировать");
        trainButton.setOnAction(event -> {
            int sliderValue = (int) valueSlider.getValue();
            List<Metrics> metrics = intConsumer.apply(sliderValue);
            metrics.forEach(metric -> textArea.appendText(metric.formatMetrics() + '\n'));
        });
        return trainButton;
    }

    private Button testButton(DoubleSupplier doubleSupplier) {
        Button testButton = new Button("Прогнать тренировочную выборку");
        testButton.setOnAction(event -> textArea.setText(
            "Результат распознавания тренировочной выборки: %.3f%%\n".formatted(doubleSupplier.getAsDouble() * 100)));
        return testButton;
    }
}

