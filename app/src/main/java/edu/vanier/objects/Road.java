/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class Road {

    private final ArrayList<Point> fitnessScores;
    private final ArrayList<Shape> roadLines;
    private boolean isFirstPut;
    private final double distanceBetweenLines;
    private int roadLenght;

    public Road() {
        this.fitnessScores = new ArrayList<>();
        this.roadLines = new ArrayList<>();
        this.isFirstPut = false;
        this.distanceBetweenLines = 50d;
        this.roadLenght = 0;
        
    }
    
    public void createRoad(Pane root) {
        if (!isFirstPut) {
            startRoad(root);
        } else {
            
        }
    }

    public ArrayList<Point> getFitnessScores() {
        return this.fitnessScores;
    }

    public ArrayList<Shape> getRoadLines() {
        return this.roadLines;
    }
    
    private void startRoad(Pane root) {
        root.setOnMouseClicked((event) -> {
            Point point = new Point(event.getX(), event.getY());
            Line firstRoad = new Line(event.getX(), event.getY() - this.distanceBetweenLines,
                    event.getX(), event.getY() + this.distanceBetweenLines);
            this.fitnessScores.add(point);
            this.roadLines.add(firstRoad);
            System.out.println("New point created " + point.getLayoutX() + ", " + point.getLayoutY());

            root.getChildren().addAll(point, firstRoad);
        });

        this.isFirstPut = true;
        this.roadLenght++;
    }
    
    private void createRoadSpace(Pane root) {
        Point latest = this.fitnessScores.get(this.roadLenght - 1);
        
        root.setOnMouseClicked((event) -> {
            Point newPoint = new Point(event.getX(), event.getY());
            System.out.println("New point created " + newPoint.getLayoutX() + ", " + newPoint.getLayoutY());
            
            double slope = findSlope(latest, newPoint);
            
        });
        
    }
    
    private void addMultipleFitnessScores() {
        
    }
    
    private double findSlope(Point a, Point b) {
        return (b.getLayoutY() - a.getLayoutY())/(b.getLayoutX()-a.getLayoutX());
    }
}
