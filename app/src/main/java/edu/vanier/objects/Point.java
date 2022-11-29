/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author edeli
 */
public class Point extends Rectangle {

    public Point() {
        this.setFill(Color.CRIMSON);
        this.setHeight(12d);
        this.setWidth(12d);
    }
    
    public Point(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFill(Color.CRIMSON);
        this.setHeight(12d);
        this.setWidth(12d);
    }
}
