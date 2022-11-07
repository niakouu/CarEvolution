/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import neuralNetwork.NeuralNetwork;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 *
 * @author enyihou
 */
public class Car extends Circle {

    Sensor[] sensors = new Sensor[7];
    public static double speed = 0.5;
    public static double angularVelocity = 5;

    int fitnessScore = 0;
    double velocity = 3 * Car.speed;

    NeuralNetwork neuralNetwork = new NeuralNetwork(7, 5, 1, 0.5);

    Color color = Color.GREEN;

    public Car(Pane root) {

        this.setRadius(15);
        this.setCenterX(65);
        this.setCenterY(130);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }

        root.getChildren().add(this);
    }

    public void move() {

        double[] inputs = new double[7];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = this.sensors[i].projectedLength.doubleValue();
        }

        double[] calculatedRotation = this.neuralNetwork.calculate(inputs);

        this.setRotate(this.getRotate() + calculatedRotation[0]);

        this.setCenterX(this.getCenterX() - this.velocity * Math.cos(Math.toRadians(this.getRotate())));
        this.setCenterY(this.getCenterY() - this.velocity * Math.sin(Math.toRadians(this.getRotate())));

    }

}
