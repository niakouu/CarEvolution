package edu.vanier.neural.network;

import java.awt.geom.NoninvertibleTransformException;
import javafx.scene.shape.Circle;

import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

enum ActivationFunction {
    NONE,
    RELU,
    SIGMOID
}

public class Neuron extends Circle {

    private static Color COLOR = Color.BISQUE;
    private int SIZE = 20;
    private static Random randomizer = new Random();
    private DoubleProperty value = new SimpleDoubleProperty();
    ActivationFunction activationFunction;

    public Neuron() {
        this.setFill(COLOR);
        this.setRadius(SIZE);
        this.activationFunction = ActivationFunction.values()[1];

    }

    public Neuron(boolean inputLayer) {
        this.setFill(COLOR);
        if (inputLayer == true) {
            this.setRadius(SIZE);
            this.activationFunction = ActivationFunction.values()[0];
        }
    }

    public Neuron(double value) {
        this.setFill(COLOR);
        this.value.setValue(value);
    }

    public void calculate(Weight[] weights, Neuron[] neurons) {
        for (Neuron neuron : neurons) {
            double total = 0;
            for (int i = 0; i < neurons.length; i++) {
                total += weights[i].getValue().doubleValue() * neurons[i].activationFunctionValue();

            }
            this.setValue(total);
        }

    }

    public DoubleProperty getValueProperty() {
        return this.value;
    }

    public double getValue() {
        return this.value.doubleValue();
    }

    public void setValue(double value) {
        this.value.setValue(value);
    }

    public static double sigmoid(double number) {
        return 1 / (1 + Math.pow(Math.E, -number));
    }

    public static double RELU(double number) {
        return Math.max(0, number);
    }

    public double activationFunctionValue() {
        switch (this.activationFunction) {
            case RELU -> {
                return RELU(this.value.doubleValue());
            }
            case SIGMOID -> {
                return sigmoid(this.value.doubleValue());
            }
            case NONE -> {
                return this.value.doubleValue();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "value: " + this.value;
    }
}
