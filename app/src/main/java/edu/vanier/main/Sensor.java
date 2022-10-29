/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;

/**
 *
 * @author enyihou
 */
class Sensor extends Line {

    double length = 130;
    DoubleProperty projectedLength = new SimpleDoubleProperty();

    public Sensor(int order, Car car) {

        /*
        if (order == 0) {
            this.setStroke(Color.RED);
        }
        if (order == 1) {
            this.setStroke(Color.ORANGE);
        }
        if (order == 2) {
            this.setStroke(Color.YELLOW);
        }
        if (order == 3) {
            this.setStroke(Color.GREEN);
        }
        if (order == 4) {
            this.setStroke(Color.BLUE);
        }
        if (order == 5) {
            this.setStroke(Color.PURPLE);
        }
        if (order == 6) {
            this.setStroke(Color.PINK);
        }
         */
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

}
