/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import edu.vanier.main.App;
import edu.vanier.neural.network.NeuralNetwork;
import edu.vanier.neural.network.NeuralNetworkDisplay;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.Cursor;
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

    public static NeuralNetworkDisplay display;

    private static double MAX_VELOCITY = 3;
    private static double MAX_ANGULAR_VELOCITY = 3;
    private static int OUTPUT_NODES_NUMBER = 3;
    private static int HIDDEN_NODES_NUMBER = 7;
    private static int SENSORS_NUMBER = 7;
    private static int HIDDEN_LAYERS = 4;
    private static int[] LAYERS = {SENSORS_NUMBER, 5, 4, OUTPUT_NODES_NUMBER};
    private static float LEARNING_RATE = 0.5f;

    private final NeuralNetworkDisplay networkDisplay;
    private double velocity;
    private double angularVelocity;
    final private Sensor[] sensors;
    final private NeuralNetwork brain;
    final private Color color;
    private double fitnessScore;
    private double timeElapsed;
    private double direction;
    private int moveStraightCounter;
    private Pane root;

    public Car(Pane root, double xPosition, double yPosition) {
        setOnDisplay();
        this.root = root;
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

        this.brain = new NeuralNetwork(LAYERS, LEARNING_RATE);

        root.getChildren().add(this);
        this.networkDisplay = new NeuralNetworkDisplay(this);
    }
    
    public Car(Pane root, NeuralNetwork brain, double xPosition, double yPosition) {
        this.root = root;
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

        this.networkDisplay = new NeuralNetworkDisplay(this);
        
    }

    public final void setOnDisplay() {

        this.setOnMouseEntered((e) -> {
            App.scene.setCursor(Cursor.HAND);
        });

        this.setOnMouseExited((e) -> {
            App.scene.setCursor(Cursor.DEFAULT);
        });
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

    public static double getMAX_VELOCITY() {
        return MAX_VELOCITY;
    }

    public static void setMAX_VELOCITY(double MAX_VELOCITY) {
        Car.MAX_VELOCITY = MAX_VELOCITY;
    }

    public static double getMAX_ANGULAR_VELOCITY() {
        return MAX_ANGULAR_VELOCITY;
    }

    public static void setMAX_ANGULAR_VELOCITY(double MAX_ANGULAR_VELOCITY) {
        Car.MAX_ANGULAR_VELOCITY = MAX_ANGULAR_VELOCITY;
    }

    public static float getLEARNING_RATE() {
        return LEARNING_RATE;
    }

    public static void setLEARNING_RATE(float LEARNING_RATE) {
        Car.LEARNING_RATE = LEARNING_RATE;
    }

    public static int getOUTPUT_NODES_NUMBER() {
        return OUTPUT_NODES_NUMBER;
    }

    public static void setOUTPUT_NODES_NUMBER(int OUTPUT_NODES_NUMBER) {
        Car.OUTPUT_NODES_NUMBER = OUTPUT_NODES_NUMBER;
    }

    public static int getHIDDEN_NODES_NUMBER() {
        return HIDDEN_NODES_NUMBER;
    }

    public static void setHIDDEN_NODES_NUMBER(int HIDDEN_NODES_NUMBER) {
        Car.HIDDEN_NODES_NUMBER = HIDDEN_NODES_NUMBER;
    }

    public static int getSENSORS_NUMBER() {
        return SENSORS_NUMBER;
    }

    public static void setSENSORS_NUMBER(int SENSORS_NUMBER) {
        Car.SENSORS_NUMBER = SENSORS_NUMBER;
    }

    public static int getHIDDEN_LAYERS() {
        return HIDDEN_LAYERS;
    }

    public static void setHIDDEN_LAYERS(int HIDDEN_LAYERS) {
        Car.HIDDEN_LAYERS = HIDDEN_LAYERS;
    }

    public static int[] getLAYERS() {
        return LAYERS;
    }

    public static void setLAYERS(int[] LAYERS) {
        Car.LAYERS = LAYERS;
    }

}
