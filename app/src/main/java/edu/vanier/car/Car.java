/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.car;

import edu.vanier.neuralNetwork.NeuralNetwork;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author enyihou
 */
public class Car extends Circle {

    private Sensor[] sensors = new Sensor[7];

    private double velocity;
    private double angularVelocity;
    private NeuralNetwork brain;
    private Color color;
    private double direction;

    public Car(Pane root) {
        this.velocity = 0.3;
        this.angularVelocity = 2;
        this.color = Color.GREEN;

        this.setRadius(15);
        this.setCenterX(100);
        this.setCenterY(90);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }
        this.brain = new NeuralNetwork(7, 5, 3, 0.3f);
        
        root.getChildren().add(this);
    }

    public double[] think() {
        return this.brain.query(sensors);
    }

    public void move() {
        this.setCenterX(this.getCenterX() + this.velocity * Math.cos(Math.toRadians(this.getRotate())));
        this.setCenterY(this.getCenterY() + this.velocity * Math.sin(Math.toRadians(this.getRotate())));
    }

    public void rotateRight() {
        this.setRotate(this.getRotate() + this.angularVelocity);
    }

    public void rotateLeft() {
        this.setRotate(this.getRotate() - this.angularVelocity);
    }

    public Sensor[] getSensors() {
        return this.sensors;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public double getAngularVelocity() {
        return this.angularVelocity;
    }

    public double getDirection() {
        return this.direction;
    }
}
