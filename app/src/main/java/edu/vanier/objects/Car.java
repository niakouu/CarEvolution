/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import edu.vanier.neuralNetwork.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author enyihou
 */
public class Car extends Circle implements Comparable<Car> {

    private final static double MAX_VELOCITY = 3;
    private final static double MAX_ANGULAR_VELOCITY = 3;
    private final static int OUTPUT_NODES_NUMBER = 3;
    private final static int HIDDEN_NODES_NUMBER = 7;
    private final static int SENSORS_NUMBER = 14;
    private final static float LEARNING_RATE = 0.5f;
    
    private double velocity;
    private double angularVelocity;  
    private final Sensor[] sensors;
    private final NeuralNetwork brain;
    private final Color color;
    private double fitnessScore;
    private double timeElapsed;
    private double direction;
    private int moveStraightCounter; 
    private boolean haveIntersect = false; 

    public boolean isHaveIntersect() {
        return haveIntersect;
    }

    public void setHaveIntersect(boolean haveIntersect) {
        this.haveIntersect = haveIntersect;
    }

    public Car(Pane root, double xPosition, double yPosition) {
        this.color = Color.GREEN;
        this.sensors = new Sensor[SENSORS_NUMBER];
        this.moveStraightCounter = 0;
        this.velocity = MAX_VELOCITY;
        this.angularVelocity = MAX_ANGULAR_VELOCITY;

        this.setRadius(15);
        this.setCenterX(xPosition);
        this.setCenterY(yPosition);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }
        this.brain = new NeuralNetwork(SENSORS_NUMBER, HIDDEN_NODES_NUMBER, OUTPUT_NODES_NUMBER, LEARNING_RATE);

        root.getChildren().add(this);
    }
    
    public Car(Pane root, NeuralNetwork brain, double xPosition, double yPosition) {
        this.color = Color.GREEN;
        this.sensors = new Sensor[SENSORS_NUMBER];
        this.moveStraightCounter = 0;
        this.velocity = MAX_VELOCITY;
        this.angularVelocity = MAX_ANGULAR_VELOCITY;
        
        this.setCenterX(xPosition);
        this.setCenterY(yPosition);
        this.setRadius(15);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }
        
        this.brain = brain;

        root.getChildren().add(this);
    }

    public void think() {
        double[] outputs = this.brain.query(this.sensors);

        double LeftIsBestOutcome = outputs[0];
        double NoRotationIsBestOutcome = outputs[1];
        double RightRotationIsBestOutcome = outputs[2];

        if (LeftIsBestOutcome > NoRotationIsBestOutcome && LeftIsBestOutcome > RightRotationIsBestOutcome) {
            this.rotateLeft();
        } else if (RightRotationIsBestOutcome > NoRotationIsBestOutcome && RightRotationIsBestOutcome > LeftIsBestOutcome) {
            this.rotateRight();
        }
        moveStraightCounter++;
        this.moveStraight();
        
    }

    public void update(ArrayList<Line> dangers) {
        for (Sensor cSensor : this.sensors) {
            boolean touched = false;
            ArrayList<Double> intersections = new ArrayList<>();
            for (int j = 0; j < dangers.size(); j++) {
                Shape shape = Shape.intersect(cSensor, dangers.get(j));
                if (shape.getBoundsInParent().getWidth() != -1) {
                    double projected = Math.sqrt(
                            Math.pow((shape.getBoundsInParent().getCenterX() - this.getCenterX()), 2)
                            + Math.pow((shape.getBoundsInParent().getCenterY() - this.getCenterY()), 2)) - this.getRadius();
                    if (projected >= 0) {
                        cSensor.setStroke(Color.RED);
                        touched = true;
                        intersections.add(projected);
                    }
                }
            }
            if (touched) {
                Collections.sort(intersections);
                cSensor.getProjectedLength().setValue(intersections.get(0));
            } else {
                cSensor.setStroke(Color.GREEN);
                cSensor.getProjectedLength().setValue(cSensor.getLength() - this.getRadius());
            }
        }
    }
    
    public void stop() {
        this.velocity = 0;
        this.angularVelocity = 0;
    }

    public void move() {

        double[] calculatedRotation = this.brain.query(sensors);

        this.setRotate(this.getRotate() + calculatedRotation[0]);

        this.setCenterX(this.getCenterX() - this.velocity * Math.cos(Math.toRadians(this.getRotate())));
        this.setCenterY(this.getCenterY() - this.velocity * Math.sin(Math.toRadians(this.getRotate())));

    }

    public void moveStraight() {
        this.setCenterX(this.getCenterX() - this.velocity * Math.cos(Math.toRadians(this.getRotate())));
        this.setCenterY(this.getCenterY() - this.velocity * Math.sin(Math.toRadians(this.getRotate())));
    }
    
    public void rotateRight() {
        this.setRotate(this.getRotate() + this.angularVelocity);
    }

    public void rotateLeft() {
        this.setRotate(this.getRotate() - this.angularVelocity);
    }

    @Override
    public int compareTo(Car o2) {
        double fitnessScore1 = this.fitnessScore;
        double fitnessScore2 = o2.getFitnessScore();

        if (fitnessScore1 > fitnessScore2) {
            return 1;
        } else if (fitnessScore1 < fitnessScore1) {
            return -1;
        } else {
            return 0;
        }
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public Sensor[] getSensors() {
        return this.sensors;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public double getAngularVelocity() {
        return this.velocity;
    }

    public double getTimeElapsed() {
        return this.timeElapsed;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public double getDirection() {
        return this.direction;
    }

    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public int getMove() {
        return this.moveStraightCounter;
    }
    
    
    
}
