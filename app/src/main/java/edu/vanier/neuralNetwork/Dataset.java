/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 2145013
 */
public class Dataset {
    
    private final double[][] rawData;
    private final List<Integer> headers;
    private final int sizeOfaData;
    private Matrix inputs;

    public Dataset(String directory, int sizeOfData) {
      this.sizeOfaData = sizeOfData;
      this.headers = new ArrayList<>();
      this.rawData = getData(directory);
      reshape();
    }

    public double[][] getRawData() {
      return this.rawData;
    }

    public List<Integer> getHeaders() {
      return this.headers;
    }

    public Matrix getInputs() {
      return this.inputs;
    }

    public float appendCorrectOrIncorrect(List<Matrix> matrixList) {
      List<Float> scorecard = new ArrayList<>();
      for (int i = 0; i < matrixList.size(); i++) {
        int label = matrixList.get(i).getHighestValuePosition();
        if (label == this.headers.get(i)) {
          scorecard.add(1.0f);
        } else {
          scorecard.add(0.0f);
        }
      }
      return calculatePerformance(scorecard);
    }

    public double[][] getInputData() {
      return this.inputs.getData();
    }

    private float calculatePerformance(List<Float> scorecard) {
      float total = 0;
      for (Float score : scorecard) {
        total += score;
      }
      return total / (float) scorecard.size() * 100f;
    }

    private void reshape() {
      double[][] reshapedInputsArray = new double[this.rawData.length][this.rawData[0].length];
      int counter = 0;
      for (double[] input : this.rawData) {
        this.headers.add((int) input[0]);
        scaleInput(input);
        reshapedInputsArray[counter++] = Arrays.copyOfRange(input, 1, input.length);
      }
      this.inputs = new Matrix(reshapedInputsArray);
    }

    private void scaleInput(double[] inputs) {
      for (int i = 0; i < inputs.length; i++) {
        inputs[i] = (inputs[i] / 255.5 * 0.99) + 0.01;
      }
    }

    private double[][] getData(String directory) {
      BufferedReader reader;
      List<double[]> dataList = new ArrayList<>();
      try {
        reader = openFile(directory);
        String line = reader.readLine();
        while (isNotNull(line)) {
          dataList.add(removeComa(line));
          line = reader.readLine();
        }
        reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return getArrayFromDataList(dataList);
    }

    private boolean isNotNull(String line) {
      return line != null;
    }

    private BufferedReader openFile(String directory) throws FileNotFoundException {
      return new BufferedReader(new FileReader(directory));
    }

    private double[] removeComa(String line) {
      String[] characters = line.split(",");
      double[] inputs = new double[characters.length];
      int counter = 0;
      for (String character : characters) {
        inputs[counter++] = Double.parseDouble(character);
      }
      return inputs;
    }

    private double[][] getArrayFromDataList(List<double[]> datalist) {
      double[][] data = new double[datalist.size()][this.sizeOfaData];
      for (int i = 0; i < data.length; i++) {
        data[i] = datalist.get(i);
      }
      return data;
    }
}
