/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.core.animations;

import edu.vanier.core.math.Equation;
import edu.vanier.objects.Point;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class RoadAnimation extends AnimationTimer {

    private final static double DISTANCE_BETWEEN_FITNESS_SCORES = 10d;
    private final ArrayList<Point> mainFitnessScores;
    private final ArrayList<Point> fitnessScores;
    private final ArrayList<Line> roadLines;
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
        super.stop();
        Point lastPoint = this.mainFitnessScores.get(this.mainFitnessScores.size() - 1);
        this.end = new Circle(lastPoint.getLayoutX(), lastPoint.getLayoutY(), DISTANCE_BETWEEN_FITNESS_SCORES * 2, Color.GREEN);
        this.startCarsPostion = this.fitnessScores.get(0);
        this.root.getChildren().add(this.end);
    }
    
    
//    public void stop() {
//        this.root.setOnMouseClicked(null);
//        this.root.getChildren().add(this.end);
//    }
    
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

    public ArrayList<Line> getRoadLines() {
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
        
        double angle = Math.atan2(
            newPoint.getLayoutY() - latest.getLayoutY(),
            newPoint.getLayoutX() - latest.getLayoutX()
        ) + Math.PI/2;
        
        System.out.println("Space: New point created " + newPoint.getLayoutX() + ", " + newPoint.getLayoutY());

        addMultipleFitnessScores(latest, newPoint);

        Line line1 = new Line(
            latest.getLayoutX() + this.distanceBetweenLines * Math.cos(angle),
            latest.getLayoutY() + this.distanceBetweenLines * Math.sin(angle),
            newPoint.getLayoutX() + this.distanceBetweenLines * Math.cos(angle), 
            newPoint.getLayoutY() + this.distanceBetweenLines * Math.sin(angle));

        Line line2 = new Line(
                latest.getLayoutX() - this.distanceBetweenLines * Math.cos(angle),
                latest.getLayoutY() - this.distanceBetweenLines * Math.sin(angle),
                newPoint.getLayoutX() - this.distanceBetweenLines * Math.cos(angle), 
                newPoint.getLayoutY() - this.distanceBetweenLines * Math.sin(angle));
        
        Equation eq1 = new Equation(line1.getEndX(), line1.getEndY(), 
                                line1.getStartX(), line1.getStartY());
        Equation eq2 = new Equation(line2.getEndX(), line2.getEndY(), 
                            line2.getStartX(), line2.getStartY());
        
        if(!this.isFirstLinePut) {
            line1.setStartX(line1.getStartX() + this.distanceBetweenLines * Math.cos(angle + Math.PI / 2));
            line1.setStartY(line1.getStartY() + this.distanceBetweenLines * Math.sin(angle + Math.PI / 2));
            line2.setStartX(line2.getStartX() + this.distanceBetweenLines * Math.cos(angle + Math.PI / 2));
            line2.setStartY(line2.getStartY() + this.distanceBetweenLines * Math.sin(angle + Math.PI / 2));
            
            Line firstLine = new Line(
                    line1.getStartX(), line1.getStartY(),
                    line2.getStartX(), line2.getStartY()
                    );
            
            this.roadLines.add(firstLine);
            this.root.getChildren().addAll(firstLine);
            this.isFirstLinePut = true;
        } else {
            Line prevLine1 = this.roadLines.get(this.roadLines.size() - 2);
            Line prevLine2 = this.roadLines.get(this.roadLines.size() - 1);
            
            Equation prevEq1 = new Equation(prevLine1.getEndX(), prevLine1.getEndY(), 
                                prevLine1.getStartX(), prevLine1.getStartY());
            Equation prevEq2 = new Equation(prevLine2.getEndX(), prevLine2.getEndY(), 
                                prevLine2.getStartX(), prevLine2.getStartY());
            
            Point intersection = eq1.getIntersection(prevEq1);
            
            line1.setStartX(intersection.getLayoutX());
            line1.setStartY(intersection.getLayoutY());
            this.roadLines.get(this.roadLines.size() - 2).setEndX(intersection.getLayoutX());
            this.roadLines.get(this.roadLines.size() - 2).setEndY(intersection.getLayoutY());
            
            intersection = eq2.getIntersection(prevEq2);
            
            line2.setStartX(intersection.getLayoutX());
            line2.setStartY(intersection.getLayoutY());
            
            this.roadLines.get(this.roadLines.size() - 1).setEndX(intersection.getLayoutX());
            this.roadLines.get(this.roadLines.size() - 1).setEndY(intersection.getLayoutY());
        }
        
        this.mainFitnessScores.add(newPoint);
        this.fitnessScores.add(newPoint);
        
        this.roadLines.add(line1);
        this.roadLines.add(line2);
        
        this.roadLenght++;

        this.root.getChildren().addAll(line1, line2, newPoint);
    }
    
    private void addMultipleFitnessScores(Point start, Point end) {
        
        double startXPoint = start.getLayoutX();
        double startYPoint = start.getLayoutY();
        double endXPoint = end.getLayoutX();
        double endYPoint = end.getLayoutY();
        
        final double hypot = Math.hypot(
            endXPoint - startXPoint,
            endYPoint - startYPoint
        );
        
        for (double i = 0; i < hypot; i += DISTANCE_BETWEEN_FITNESS_SCORES) {
            double angle = Math.atan2(
                endYPoint - startYPoint,
                endXPoint - startXPoint
            );
            
            Point fitnessScore = new Point(
                startXPoint + Math.cos(angle) * i,
                startYPoint + Math.sin(angle) * i
            );
            
            this.fitnessScores.add(fitnessScore);
        }    
    }
    
    public ArrayList<Line> getDangers() {
        return this.roadLines;
    }
    
    public Point getStartPoint() {
        return this.startCarsPostion;
    }
    
}
