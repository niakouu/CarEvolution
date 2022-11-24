/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.animations;

import edu.vanier.math.Equation;
import edu.vanier.objects.Point;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class RoadAnimation extends AnimationTimer {

    private final ArrayList<Point> mainFitnessScores;
    private final ArrayList<Point> fitnessScores;
    private final ArrayList<Shape> roadLines;
    private final Pane root;
    private boolean isFirstPointPut;
    private boolean isFirstLinePut;
    private Point startCarsPostion;
    private Circle end;
    private final double distanceBetweenLines;
    private int roadLenght;

    public RoadAnimation(Pane root) {
        this.mainFitnessScores = new ArrayList<>();
        this.roadLines = new ArrayList<>();
        this.fitnessScores = new ArrayList<>();
        this.isFirstPointPut = false;
        this.isFirstLinePut = false;
        this.distanceBetweenLines = 30d;
        this.root = root;
        this.roadLenght = 0;
        this.end = new Circle(60d);
        this.startCarsPostion = new Point();
    }
    
    @Override
    public void handle(long now) {
        
    }
    
    @Override
    public void start(){
        createRoad();
    }
    
    @Override
    public void stop() {
        
    }
    
    public void reset() {
        for(Point fitnessScore : this.mainFitnessScores) {
            this.root.getChildren().remove(fitnessScore);
        }
        for(Point fitnessScore : this.fitnessScores) {
            this.root.getChildren().remove(fitnessScore);
        }
        for(Shape line : this.roadLines) {
            this.root.getChildren().remove(line);
        }
        
        this.mainFitnessScores.clear();
        this.fitnessScores.clear();
        this.roadLines.clear();
        
        this.root.getChildren().remove(end);
        
        this.isFirstPointPut = false;
        this.isFirstLinePut = false;
        this.roadLenght = 0;
    }
    
    private void createRoad() {
        this.root.setOnMouseClicked((event) -> {
            if (!this.isFirstPointPut) {
                startRoad(event);
            } else {
                createRoadSpace(event);
            }
        });
        
    }

    public ArrayList<Point> getFitnessScores() {
        return this.mainFitnessScores;
    }

    public ArrayList<Shape> getRoadLines() {
        return this.roadLines;
    }
    
    private void startRoad(MouseEvent event) {
        this.isFirstPointPut = true;
        this.roadLenght++;
        
        Point point = new Point(event.getX(), event.getY());
        
        this.mainFitnessScores.add(point);
        this.fitnessScores.add(point);
        System.out.println("Start: New point created " + point.getLayoutX() + ", " + point.getLayoutY());
        
        this.root.getChildren().add(point);
    }
    
    private void createRoadSpace(MouseEvent event) {
        Point latest = this.mainFitnessScores.get(this.roadLenght - 1);
        Point newPoint = new Point(event.getX(), event.getY());
        
        System.out.println("Space: New point created " + newPoint.getLayoutX() + ", " + newPoint.getLayoutY());

        Equation equation = new Equation(latest, newPoint);
        // TODO: set the fitness score points all

        Line line1 = new Line(latest.getLayoutX() + this.distanceBetweenLines,
                            latest.getLayoutY() + this.distanceBetweenLines,
                            newPoint.getLayoutX() + this.distanceBetweenLines, 
                            newPoint.getLayoutY() + this.distanceBetweenLines);

        Line line2 = new Line(latest.getLayoutX() - this.distanceBetweenLines,
                            latest.getLayoutY() - this.distanceBetweenLines,
                            newPoint.getLayoutX() - this.distanceBetweenLines, 
                            newPoint.getLayoutY() - this.distanceBetweenLines);
        
        if(!this.isFirstLinePut) {
            Line firstLine = new Line(latest.getLayoutX() + this.distanceBetweenLines,
                            latest.getLayoutY() + this.distanceBetweenLines,
                            latest.getLayoutX() - this.distanceBetweenLines,
                            latest.getLayoutY() - this.distanceBetweenLines);
            
            
            this.roadLines.add(firstLine);
            this.root.getChildren().addAll(firstLine);
            this.isFirstLinePut = true;
        }
        
        
        
        this.mainFitnessScores.add(newPoint);
        this.fitnessScores.add(newPoint);
        
        this.roadLines.add(line1);
        this.roadLines.add(line2);
        
        this.roadLenght++;

        this.root.getChildren().addAll(line1, line2, newPoint);
    }
    
    private void addMultipleFitnessScores() {
        
    }
    
}
