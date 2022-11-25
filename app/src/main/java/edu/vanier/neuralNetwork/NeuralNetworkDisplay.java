/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

import edu.vanier.objects.Car;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author enyihou
 */
public class NeuralNetworkDisplay extends Pane {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private static final int PANE_PADDING = 20;

    private Car displayedCar;
    private NeuralNetwork neuralNetwork;
    private Neuron[][] neurons;
    private Weight[][][] weights;

    public NeuralNetworkDisplay(Car car) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.displayedCar = car;
        this.neurons = displayedCar.getBrain().getNeurons();
        this.neuralNetwork = displayedCar.getBrain();
        this.weights = displayedCar.getBrain().getWeights();
        generateNeurons();
        generateWeight();
    }

    private void generateNeurons() {
        int numberOfLayers = neurons.length;
        int disposableWidth = WIDTH - 2 * PANE_PADDING;
        int disposableHeight = HEIGHT - 2 * PANE_PADDING;
        int layerGap = disposableWidth / (numberOfLayers + 1);

        for (int i = 0; i < neurons.length; i++) {
            int heighGap = disposableHeight / (neurons[i].length + 1);
            for (int j = 0; j < neurons[i].length; j++) {

                Label value = new Label();
                value.textProperty().bind(neurons[i][j].getValueProperty().asString("%.2f"));

                neurons[i][j].setLayoutX(layerGap * (i + 1));
                neurons[i][j].setLayoutY(heighGap * (j + 1));
                value.setLayoutX(layerGap * (i + 1));
                value.setLayoutY(heighGap * (j + 1));

                this.getChildren().addAll(neurons[i][j], value);

            }
        }

    }

    private void generateWeight() {
        for (Weight[][] i : weights) {
            for (Weight[] j : i) {
                for (Weight k : j) {
                    this.getChildren().add(k);
                }
            }
        }
    }

    public Car getDisplayedCar() {
        return displayedCar;
    }

    public void setDisplayedCar(Car displayedCar) {
        this.neurons = displayedCar.getBrain().getNeurons();
        this.neuralNetwork = displayedCar.getBrain();
        this.weights = displayedCar.getBrain().getWeights();
        this.displayedCar = displayedCar;

    }

}
