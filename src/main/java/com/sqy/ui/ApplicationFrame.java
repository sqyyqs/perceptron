package com.sqy.ui;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.sqy.MultiLayerPerceptronRunner;
import com.sqy.core.MultiLayerPerceptron;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApplicationFrame extends Application {




    @Override
    public void start(Stage primaryStage) {
        PixelCanva pixelCanva = new PixelCanva(512, 512);
        pixelCanva.drawGrid(16, Color.LIGHTGRAY);

        Consumer<Integer> firstCallback = (value) -> {
            System.out.println("First button pressed, value: " + value);
        };

        Runnable secondCallback = () -> {
            System.out.println("Second button pressed!");
        };


        BorderPane root = new BorderPane();
        root.setTop(topControls(pixelCanva, firstCallback, secondCallback));
        StackPane canvasContainer = new StackPane(pixelCanva);
        root.setCenter(canvasContainer);

        Scene scene = new Scene(root, 1000, 700);

        primaryStage.setTitle("Pixel Painting Canvas with Slider and Callbacks");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private HBox topControls(PixelCanva pixelCanva, Consumer<Integer> sliderButtonCallback, Runnable secondButtonCallback) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);  // Default color
        colorPicker.setOnAction(event -> pixelCanva.setCurrentColor(colorPicker.getValue()));

        Slider valueSlider = new Slider(5, 20, 50);
        valueSlider.setShowTickLabels(true);
        valueSlider.setShowTickMarks(true);
        valueSlider.setMajorTickUnit(5);
        valueSlider.setBlockIncrement(5);

        Label sliderValueLabel = new Label("Количество эпох: " + (int) valueSlider.getValue());

        valueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderValueLabel.setText("Value: " + newValue.intValue());
        });

        Button transformButton = new Button("Распознать изображение");
        transformButton.setOnAction(event -> {
            double[] canvasData = pixelCanva.transformCanvasToDoubleArray();

            System.out.println(Arrays.toString(canvasData));
            System.out.println(canvasData.length);
        });

        Button button1 = new Button("Тренировать");
        button1.setOnAction(event -> {
            int sliderValue = (int) valueSlider.getValue();
            sliderButtonCallback.accept(sliderValue);
        });

        Button clearButton = new Button("Очистить холст");
        clearButton.setOnAction(event -> {
            pixelCanva.clearCanvas();
            pixelCanva.drawGrid(10, Color.LIGHTGRAY);
        });

        Button button2 = new Button("Call Second Callback");
        button2.setOnAction(event -> secondButtonCallback.run());

       return new HBox(10, colorPicker, valueSlider, sliderValueLabel, button1, button2, transformButton, clearButton);
    }
}

