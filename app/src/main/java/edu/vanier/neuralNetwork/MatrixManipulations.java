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
    
    public static Matrix addMatrix(Matrix m1, Matrix m2) {
        return operationGenerator(Operator.ADDITION, m1, m2);
    }

    public static Matrix subtractMatrix(Matrix minuend, Matrix subtrahend) {
        return operationGenerator(Operator.SUBSTRACTION, minuend, subtrahend);
    }

    public static Matrix subtractMatrix(double minuend, Matrix subtrahend) {
        return operationGenerator(Operator.SUBSTRACTION, minuend, subtrahend);
    }

    public static Matrix multiplyMatrixElementByElement(double num, Matrix matrix) {
        return operationGenerator(Operator.MULTIPLICATION, num, matrix);
    }
    
    public static Matrix multiplyMatrixElementByElement(Matrix m1, Matrix m2) {
        return operationGenerator(Operator.MULTIPLICATION, m1, m2);
    }
    
    public static Matrix averageMatrixElementByElement(Matrix m1, Matrix m2) {
        return operationGenerator(Operator.DIVISION, operationGenerator(Operator.ADDITION, m1, m2), 2);
    }

    public static Matrix multiplyMatrixElementByElement(Matrix m1, Matrix m2, Matrix m3) {
        return operationGenerator(Operator.MULTIPLICATION, m1, m2, m3);
    }

    public static Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix[] arr = makeRowOfFirstMatrixBeEqualToColumnOfSecondMatrix(m1, m2);
        int rows = arr[0].getNumberOfRows();
        int column = arr[1].getNumberOfColumns();
        double[][] lessRows = arr[0].getData();
        double[][] moreRows = arr[1].getData();
        double[][] result = new double[rows][column];
        for (int i = 0; i < result.length; i++) {
          int counterColumns = 0;
          for (int j = 0; j < result[i].length; j++) {
            result[i][j] = multiplyRowByColumn(lessRows[i], moreRows, counterColumns++);
          }
        }
        return new Matrix(result);
    }
    
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
    
    public static Matrix mutate(Matrix matrix, double rate){
        double[][] result = new double[matrix.getNumberOfRows()][matrix.getNumberOfColumns()];
        for (int i = 0; i < result.length; i++) {
          for (int j = 0; j < result[i].length; j++) {
                if (Math.random() < rate) {
                    result[i][j] = Math.random() * 2 - 1;
                } else {
                    result[i][j] = matrix.getData()[i][j];
                }
            }
        }
        return new Matrix(result);
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
    
     private static Matrix operationGenerator(Operator operator, Matrix m1, Matrix m2) {
    double[][] result = new double[m1.getNumberOfRows()][m1.getNumberOfColumns()];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        result[i][j] = operator.apply(m1.getData()[i][j], m2.getData()[i][j]);
      }
    }
    return new Matrix(result);
  }

  private static Matrix operationGenerator(Operator operator, double m1, Matrix m2) {
    double[][] result = new double[m2.getNumberOfRows()][m2.getNumberOfColumns()];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        result[i][j] = operator.apply(m1, m2.getData()[i][j]);
      }
    }
    return new Matrix(result);
  }
  
  private static Matrix operationGenerator(Operator operator, Matrix m2, double m1) {
    double[][] result = new double[m2.getNumberOfRows()][m2.getNumberOfColumns()];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        result[i][j] = operator.apply(m2.getData()[i][j], m1);
      }
    }
    return new Matrix(result);
  }

  private static Matrix operationGenerator(Operator operator, Matrix m1, Matrix m2, Matrix m3) {
    double[][] result = new double[m1.getNumberOfRows()][m1.getNumberOfColumns()];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        result[i][j] = operator.apply(m1.getData()[i][j], m2.getData()[i][j]);
        result[i][j] = operator.apply(result[i][j], m3.getData()[i][j]);
      }
    }
    return new Matrix(result);
  }

  private static Matrix[] makeRowOfFirstMatrixBeEqualToColumnOfSecondMatrix(Matrix m1, Matrix m2) {
    if (twoSidesAreSimilar(m1, m2) && m2.getNumberOfColumns() == m1.getNumberOfRows()) {
      return new Matrix[]{m1, m2};

    } else if (twoSidesAreSimilar(m1, m2) && m1.getNumberOfColumns() == m2.getNumberOfColumns()) {
      return new Matrix[]{m1, transpose(m2)};

    } else if (m1.getNumberOfColumns() == m2.getNumberOfRows() && isInsideNumberSmallerThenTheOutside(m1.getNumberOfRows(),
        m2.getNumberOfColumns(), m2.getNumberOfRows())) {

      return new Matrix[]{m2.getNumberOfColumns() > m1.getNumberOfRows() ? transpose(m1) : transpose(m2),
          m2.getNumberOfColumns() > m1.getNumberOfRows() ? transpose(m2) : transpose(m1)};

    } else if (m1.getNumberOfColumns() == m2.getNumberOfRows()) {
      return new Matrix[]{m2.getNumberOfColumns() > m1.getNumberOfRows() ? m1 : m2,
          m2.getNumberOfColumns() > m1.getNumberOfRows() ? m2 : m1};

    } else if (m1.getNumberOfColumns() == m2.getNumberOfColumns() && isInsideNumberSmallerThenTheOutside(m1.getNumberOfRows(),
        m2.getNumberOfRows(), m2.getNumberOfColumns())) {
      return new Matrix[]{m2.getNumberOfRows() > m1.getNumberOfRows() ? m1 : transpose(m2),
          m2.getNumberOfRows() > m1.getNumberOfRows() ? transpose(m2) : m1};

    } else if (m1.getNumberOfColumns() == m2.getNumberOfColumns()) {
      return new Matrix[]{m2.getNumberOfRows() > m1.getNumberOfRows() ? transpose(m2) : m1,
          m2.getNumberOfRows() > m1.getNumberOfRows() ? m1 : transpose(m2)};

    } else if (m1.getNumberOfRows() == m2.getNumberOfColumns() && isInsideNumberSmallerThenTheOutside(m1.getNumberOfColumns(),
        m2.getNumberOfRows(), m2.getNumberOfColumns())) {
      return new Matrix[]{m2.getNumberOfRows() > m1.getNumberOfColumns() ? transpose(m2) : transpose(m1),
          m2.getNumberOfRows() > m1.getNumberOfColumns() ? transpose(m1) : transpose(m2)};

    } else if (m1.getNumberOfRows() == m2.getNumberOfColumns()) {
      return new Matrix[]{m2.getNumberOfRows() > m1.getNumberOfColumns() ? m1 : m2,
          m2.getNumberOfRows() > m1.getNumberOfColumns() ? m2 : m1};

    } else if (m1.getNumberOfRows() == m2.getNumberOfRows() && isInsideNumberSmallerThenTheOutside(m1.getNumberOfColumns(),
        m2.getNumberOfColumns(), m2.getNumberOfRows())) {
      return new Matrix[]{m2.getNumberOfColumns() > m1.getNumberOfColumns()? transpose(m2) : transpose(m1),
          m2.getNumberOfColumns() > m1.getNumberOfColumns() ? m1 : m2};

    } else if (m1.getNumberOfRows() == m2.getNumberOfRows()) {
      return new Matrix[]{m2.getNumberOfColumns() > m1.getNumberOfColumns() ? m2 : transpose(m1),
          m2.getNumberOfColumns() > m1.getNumberOfColumns() ? transpose(m1) : m2};
    }
    return null;
  }

  private static boolean isInsideNumberSmallerThenTheOutside(int outside1, int outside2,
      int inside) {
    return outside1 > inside || outside2 > inside;
  }

  private static boolean twoSidesAreSimilar(Matrix m1, Matrix m2) {
    return m1.getNumberOfColumns() == m2.getNumberOfRows() && m1.getNumberOfRows() == m2.getNumberOfColumns()
        || m1.getNumberOfRows() == m2.getNumberOfRows() && m1.getNumberOfColumns() == m2.getNumberOfColumns();
  }

  private static double multiplyRowByColumn(double[] row, double[][] columns, int column) {
    double total = 0.0;
    int counterColumnForRow = 0;
    if (isThereOnlyOneRow(row)) {
      for (double[] rowInColumn : columns) {
        total += rowInColumn[column] * row[0];
      }
    } else {
      for (double[] rowInColumn : columns) {
        total += rowInColumn[column] * row[counterColumnForRow++];
      }
    }
    return total;
  }

  private static boolean isThereOnlyOneRow(double[] row) {
    return row.length == 1;
  }
}
