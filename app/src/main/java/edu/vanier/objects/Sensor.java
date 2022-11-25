/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;

/**
 *
 * @author enyihou
 */
public class Sensor extends Line {

    private double length = 200;
    private final DoubleProperty projectedLength = new SimpleDoubleProperty();

    public Sensor(int order, Car car) {

        this.startXProperty().bind(car.centerXProperty());
        this.startYProperty().bind(car.centerYProperty());

        this.endXProperty().bind(Bindings.createDoubleBinding(()
                -> car.centerXProperty().get() - this.length * Math.cos(
                Math.toRadians((-90 + 30 * order) + car.rotateProperty().get())),
                car.centerXProperty(), car.rotateProperty()));
        this.endYProperty().bind(Bindings.createDoubleBinding(()
                -> car.centerYProperty().get() - this.length * Math.sin(
                Math.toRadians((-90 + 30 * order) + car.rotateProperty().get())),
                car.centerYProperty(), car.rotateProperty()));

    }

    public double getLength() {
        return this.length;
    }

    public DoubleProperty getProjectedLength() {
        return this.projectedLength;
    }
}
