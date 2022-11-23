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

    public Road() {
        this.fitnessScores = new ArrayList<>();
        this.roadLines = new ArrayList<>();
        this.isFirstPut = false;
        this.distanceBetweenLines = 100d;
        
    }
    
    public void createRoad(Pane root) {
        if (!isFirstPut) {
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
        } else {
            
        }
    }

    public ArrayList<Point> getFitnessScores() {
        return this.fitnessScores;
    }

    public ArrayList<Shape> getRoadLines() {
        return this.roadLines;
    }
}
