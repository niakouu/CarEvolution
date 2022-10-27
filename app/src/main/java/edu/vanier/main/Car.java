/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author enyihou
 */
public class Car extends Circle {

    Sensor[] sensors = new Sensor[7];

    double velocity = 0.3;
    double angularVelocity = 2;

    Color color = Color.GREEN;

    public Car(Pane root) {

        this.setRadius(15);
        this.setCenterX(500);
        this.setCenterY(500);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }

        root.getChildren().add(this);
    }

    public void move() {
        this.setCenterX(this.getCenterX() + this.velocity * Math.cos(Math.toRadians(this.getRotate())));
        this.setCenterY(this.getCenterY() + this.velocity * Math.sin(Math.toRadians(this.getRotate())));

    }
    
    public void rotateRight(){
        this.setRotate(this.getRotate() + this.angularVelocity);
    }
    public void rotateLeft(){
        this.setRotate(this.getRotate() - this.angularVelocity);
    }
    

}
