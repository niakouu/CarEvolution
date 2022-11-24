/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.math;

import edu.vanier.objects.Point;

/**
 *
 * @author edeli
 */
public class Equation {
    
    private final double slope;
    private final double intercept;

    public Equation(Point a, Point b) {
        this.slope = (b.getLayoutY() - a.getLayoutY())/(b.getLayoutX()-a.getLayoutX());
        this.intercept = (b.getLayoutY() - this.slope * b.getLayoutX());
        System.out.println("Equation : " + this.slope + ", " + this.intercept);
    }
    
    public double getY(double x) {
        return this.slope * x + this.intercept;
    }
    
}
