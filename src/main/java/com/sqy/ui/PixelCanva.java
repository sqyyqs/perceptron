package com.sqy.ui;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PixelCanva extends Canvas {
    private int width;
    private int height;
    private Color currentColor = Color.BLACK;
    private int pixelSize = 10;

    public PixelCanva(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
        clearCanvas();
        enablePainting();
    }

    public void setPixel(int x, int y, Color color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            getGraphicsContext2D().getPixelWriter().setColor(x, y, color);
        }
    }

    public void clearCanvas() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

    private void enablePainting() {
        addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            paint(event.getX(), event.getY());
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            paint(event.getX(), event.getY());
        });
    }

    private void paint(double x, double y) {
        int gridX = (int) x / pixelSize * pixelSize;
        int gridY = (int) y / pixelSize * pixelSize;
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(currentColor);
        gc.fillRect(gridX, gridY, pixelSize, pixelSize);
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void drawGrid(int pixelSize, Color gridColor) {
        this.pixelSize = pixelSize;
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(gridColor);

        for (int x = 0; x <= width; x += pixelSize) {
            gc.strokeLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += pixelSize) {
            gc.strokeLine(0, y, width, y);
        }
    }

    public double[] transformCanvasToDoubleArray() {
        int width = (int) getWidth() / pixelSize;
        int height = (int) getHeight() / pixelSize;
        double[] pixelArray = new double[width * height];

        WritableImage writableImage = new WritableImage((int) getWidth(), (int) getHeight());
        this.snapshot(null, writableImage);

        PixelReader pixelReader = writableImage.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = pixelReader.getColor(x * pixelSize + pixelSize / 2, y * pixelSize + pixelSize / 2);
                pixelArray[y * width + x] = (pixelColor.equals(Color.WHITE)) ? 0.0 : 1.0;
            }
        }
        return pixelArray;
    }
}
