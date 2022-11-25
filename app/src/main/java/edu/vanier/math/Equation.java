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
    
    public Equation(double x1, double y1, double x2, double y2) {
        this.slope = (y2 - y1) / (x2 - x1);
        this.intercept = y1 - this.slope * x1;
    }
    
    public double getY(double x) {
        return this.slope * x + this.intercept;
    }
    
    public Point getIntersection(Equation equation) {
        double x = (equation.intercept - this.intercept)/(this.slope - equation.slope);
        double y = getY(x);
        return new Point(x, y);
    }
}
