/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import edu.vanier.neuralNetwork.NeuralNetwork;
import edu.vanier.neuralNetwork.NeuralNetworkDisplay;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author enyihou
 */
public class Car extends Circle implements Comparable<Car> {

    public static NeuralNetworkDisplay display;

    private final static double MAX_VELOCITY = 2;
    private final static double MAX_ANGULAR_VELOCITY = 2;
    private final static int OUTPUT_NODES_NUMBER = 3;
    private final static int HIDDEN_NODES_NUMBER = 7;
    private final static int SENSORS_NUMBER = 7;
    private final static float LEARNING_RATE = 0.7f;

    private final NeuralNetworkDisplay networkDisplay;
    private double velocity;
    private double angularVelocity;
    private Sensor[] sensors;
    private NeuralNetwork brain;
    private Color color;
    private double fitnessScore;
    private double timeElapsed;
    private double direction;
    private int moveStraightCounter;
    private Pane root;
    private boolean haveIntersect = false;

    public boolean isHaveIntersect() {
        return haveIntersect;
    }

    public void setHaveIntersect(boolean haveIntersect) {
        this.haveIntersect = haveIntersect;
    }

    public Car(Pane root) {
        setOnDisplay();
        this.root = root;
        this.color = Color.GREEN;
        this.sensors = new Sensor[SENSORS_NUMBER];
        this.moveStraightCounter = 0;
        this.velocity = MAX_VELOCITY;
        this.angularVelocity = MAX_ANGULAR_VELOCITY;

        this.setRadius(15);
        this.setCenterX(65);
        this.setCenterY(130);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }
        this.brain = new NeuralNetwork(new int[]{SENSORS_NUMBER, 6, 5, 4, OUTPUT_NODES_NUMBER}, LEARNING_RATE);

        root.getChildren().add(this);
        this.networkDisplay = new NeuralNetworkDisplay(this);
    }

    public Car(Pane root, NeuralNetwork brain) {

        setOnDisplay();
        this.root = root;
        this.color = Color.GREEN;
        this.sensors = new Sensor[SENSORS_NUMBER];
        this.moveStraightCounter = 0;
        this.velocity = MAX_VELOCITY;
        this.angularVelocity = MAX_ANGULAR_VELOCITY;

        this.setRadius(15);
        this.setCenterX(65);
        this.setCenterY(130);
        this.setFill(color);
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }

        this.brain = brain;

        root.getChildren().add(this);

        this.networkDisplay = new NeuralNetworkDisplay(this);
    }

    public final void setOnDisplay() {
        this.setOnMouseClicked((e) -> {

            if (root.getChildren().contains(display)) {
                root.getChildren().remove(display);
            }
            display = (this.networkDisplay);

            if (!root.getChildren().contains(display)) {
                root.getChildren().add(display);
            }

        });
    }

    public void think() {
        double[] outputs = this.brain.calculate(this.sensors);

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

    public void update(ArrayList<Shape> dangers) {
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

        double[] calculatedRotation = this.brain.calculate(sensors);

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
        } else if (fitnessScore1 < fitnessScore2) {

            return -1;
        } else {
            return 0;
        }
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
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

    public void setBrain(NeuralNetwork brain) {
        for (int i = 0; i < brain.getWeights().length; i++) {
            for (int j = 0; j < brain.getWeights()[i].length; j++) {
                for (int k = 0; k < brain.getWeights()[i][j].length; k++) {

                    this.brain.getWeights()[i][j][k].setValue(brain.getWeights()[i][j][k].getValue().doubleValue());

                }
            }
        }
    }

    public int getMove() {
        return this.moveStraightCounter;
    }

    @Override
    public String toString() {
        return "Car{" + "fitnessScore =" + fitnessScore + '}';
    }

}
