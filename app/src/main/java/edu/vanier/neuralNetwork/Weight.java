/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;

/**
 *
 * @author enyihou
 */
public class Weight extends Line {

    public Weight() {

        this.opacityProperty().bind((this.value.divide(2)).add(0.5));
        this.strokeWidthProperty().bind(((this.value.divide(2)).add(0.5)).multiply(3));

        this.setOnMouseClicked((e) -> {
            System.out.println(this.value.get());
        });
        this.toBack();

    }

    private DoubleProperty value = new SimpleDoubleProperty();

    private Neuron neuron1;
    private Neuron neuron2;

    public Neuron getNeuron1() {
        return neuron1;
    }

    public void setNeuron1(Neuron neuron1) {
        this.neuron1 = neuron1;
    }

    public Neuron getNeuron2() {
        return neuron2;
    }

    public void setNeuron2(Neuron neuron2) {
        this.neuron2 = neuron2;
    }

    public Weight(Neuron neuron1, Neuron neuron2) {

        this.opacityProperty().bind((this.value.divide(2)).add(0.5));
        this.strokeWidthProperty().bind(((this.value.divide(2)).add(0.5)).multiply(3));

        this.setOnMouseClicked((e) -> {
            System.out.println(this.value.get());
            this.toBack();
        });

        this.neuron1 = neuron1;
        this.neuron2 = neuron2;

    }

    public void bindStart(Neuron neuron) {
        this.startXProperty().bind(neuron.layoutXProperty());
        this.startYProperty().bind(neuron.layoutYProperty());
    }
    
    
    public void bindEnd(Neuron neuron) {
        this.endXProperty().bind(neuron.layoutXProperty());
        this.endYProperty().bind(neuron.layoutYProperty());
    }

    public DoubleProperty getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value.setValue(value);
    }

}
