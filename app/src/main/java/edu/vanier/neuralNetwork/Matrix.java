/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

/**
 *
 * @author 2145013
 */
public class Matrix implements Cloneable {

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
    
    public Matrix clone() {
        double[][] cloned = new double[numberOfRows][numberOfColumns];
        
        for (int i = 0; i < this.numberOfRows; ++i)
            for (int j = 0; j < this.numberOfColumns; ++j)
                cloned[i][j] = this.data[i][j];
        
        return new Matrix(cloned);
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
        double highest = 0.0;
        double value;
        int position = 0;
        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfColumns(); j++) {
                value = getData()[i][j];
                if (value > highest) {
                    highest = value;
                    position = j;
                }
            }
        }
        return position;
    }

    public int getHighestValuePosition() {
        double highest = 0.0;
        int position = 0;
        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfColumns(); j++) {
                if (this.data[i][j] > highest) {
                    highest = this.data[i][j];
                    position = j;
                }
            }
        }
        return position;
    }
    
    public void set(int row, int column, double newValue) {
        this.data[row][column] = newValue;
    }

    public double get(int row, int column) {
        return this.data[row][column];
    }
}
