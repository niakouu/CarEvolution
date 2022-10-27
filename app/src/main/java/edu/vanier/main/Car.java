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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author enyihou
 */
public class Car extends Circle{
    
    Sensor[] sensors = new Sensor[7];
    
    double velocity;
    DoubleProperty degree = new SimpleDoubleProperty(0);
    
    Color color = Color.GREEN;
    
    
    

    public Car(Pane root) {
        
        this.setRadius(15);
        this.setCenterX(75);
        this.setCenterY(130);
        this.setFill(color);
        for(int i = 0; i< sensors.length; i++){
            sensors[i] = new Sensor(i, this);
            root.getChildren().add(sensors[i]);
        }
        
        root.getChildren().add(this);
    }
    
    
    
    
    
    
}
