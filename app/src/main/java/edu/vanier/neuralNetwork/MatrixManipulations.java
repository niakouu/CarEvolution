/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

/**
 *
 * @author 2145013
 */
public class MatrixManipulations {
    
    public static Matrix transpose(Matrix matrix) {
        double[][] result = new double[matrix.getNumberOfColumns()][matrix.getNumberOfRows()];
        for (int i = 0; i < matrix.getNumberOfRows(); i++) {
            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
                result[j][i] = matrix.getData()[i][j];
            }
        }
        return new Matrix(result);
    }
    
    public static Matrix transformArrayToMatrix(int rows, int columns, double[] oneDimensional) {
        double[][] weights = new double[rows][columns];
        int rowsCounter = 0;
        int columnsCounter = 0;
        for(double weight : oneDimensional) {
            if (rowsCounter == rows && columnsCounter == columns) {
                return new Matrix(weights);
            } else if (columnsCounter == columns) {
                columnsCounter = 0;
                weights[++rowsCounter][columnsCounter++] = weight;
            } else {
                weights[rowsCounter][columnsCounter++] = weight;
            }
        }
        return new Matrix(weights);
    }
    
    public static Matrix sigmoid(Matrix matrix) {
        double[][] arr = matrix.getData();
        for (int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                arr[i][j] = sigmoidForEach(arr[i][j]);
            }
        }
        return new Matrix(arr);
    }

    private static double sigmoidForEach(double x) {
        return 1.0 / (1.0 + Math.pow(Math.E, -1.0 * x));
    }
    
    private static Matrix operationGenerator(Operator operator, double m1, Matrix m2){
        
    }
}
