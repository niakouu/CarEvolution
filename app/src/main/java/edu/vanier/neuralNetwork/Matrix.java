/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

/**
 *
 * @author 2145013
 */
public class Matrix {
    private final int numberOfColumns;
    private final int numberOfRows;
    private final double[][] data;
    private final double[] firstRow;
    
    public Matrix(double[][] inputs) {
        this.numberOfColumns = inputs[0].length;
        this.numberOfRows = inputs.length;
        this.data = inputs;
        this.firstRow = inputs[0];
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public double[][] getData() {
        return this.data;
    }

    public double[] getFirstRow() {
        return this.firstRow;
    }
    
    public int getRowPositionForWhichAdditionOfValuesIsHighest() {
        double heighest = 0.0;
        return 0;
    }
}
