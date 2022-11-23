/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import javafx.scene.shape.Circle;

/**
 *
 * @author edeli
 */
public class Point {
    
    private final double x;
    private final double y;
    private final Circle visualPoint;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        visualPoint = new Circle(x, y, 2d);
    }
    
    public void show(boolean isShow) {
        if(isShow){
            
        }
    }
}
