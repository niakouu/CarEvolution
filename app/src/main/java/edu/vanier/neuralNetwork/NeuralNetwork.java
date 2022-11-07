/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 2145013
 */
public class NeuralNetwork {
    
    private final static int DEFAULT_NODES = 3;
    private final static float DEFAULT_LEARNING_RATE = 0.3f;
    private final static double INITIAL_VALUE_TARGETS = 0.1;
    
    private final int inputNodes;
    private final int hiddenNodes;
    private final int outputNodes;
    private final float learningRate;
    private Matrix weightsInputToHidden;
    private Matrix weightsHiddenToOutput;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, float learningRate) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.learningRate = learningRate;
        generateLinkWeights();
    }

    public Matrix getWeightsInputToHidden() {
      return this.weightsInputToHidden;
    }

    public Matrix getWeightsHiddenToOutput() {
      return this.weightsHiddenToOutput;
    }

    @Override
    public String toString() {
      return "input nodes = " + inputNodes + "; hidden nodes = " + hiddenNodes + "; output nodes = "
          + outputNodes + "; learning rate = " + learningRate;
    }

    public List<Matrix> query(Dataset data) {
      List<Matrix> outputs = new ArrayList<>();
      for (double[] input : data.getInputData()) {
        Matrix inputs = getReformedInput(input);
        Matrix output = getFinalOutput(inputs);
        outputs.add(output);
      }
      return outputs;
    }

    public void trainData(Dataset data, int epochs) {
      for (int i = 0; i < epochs; i++) {
        int counter = 0;
        for (double[] input : data.getInputs().getData()) {
          Matrix inputs = getReformedInput(input);
          int target = data.getHeaders().get(counter++);
          Matrix targets = initialTargetsValues(this.outputNodes, target);
          rescaleWeight(inputs, targets);
        }
      }
    }

    private Matrix getReformedInput(double[] input) {
      return MatrixManipulations.transformArrayToMatrix(1, input.length, input);
    }

    private Matrix getFinalOutput(Matrix inputs) {
      NeuralNetworkHiddenElements neural = new NeuralNetworkHiddenElements(inputs, this);
      return neural.getFinalOutputs();
    }

    private Matrix initialTargetsValues(int length, int target) {
      double[] result = new double[length];
      Arrays.fill(result, INITIAL_VALUE_TARGETS);
      result[target] = 0.99;
      return MatrixManipulations.transformArrayToMatrix(this.outputNodes, 1, result);
    }

    private void rescaleWeight(Matrix inputs, Matrix targets) {
      NeuralNetworkHiddenElements components = new NeuralNetworkHiddenElements(inputs, targets, this);
      this.weightsInputToHidden = MatrixManipulations.addMatrix(
          rescaleWeight(components.getHiddenErrors(), components.getHiddenOutputs(), inputs),
          this.weightsInputToHidden);
      this.weightsHiddenToOutput = MatrixManipulations.addMatrix(
          rescaleWeight(components.getOutputErrors(), components.getFinalOutputs(),
              components.getHiddenOutputs()), this.weightsHiddenToOutput);
    }

    private Matrix rescaleWeight(Matrix errors, Matrix outputs, Matrix layerInBetween) {
      return MatrixManipulations.multiplyMatrixElementByElement(this.learningRate,
          getWeightsError(errors, outputs, layerInBetween));
    }

    private Matrix getWeightsError(Matrix errors, Matrix outputs, Matrix layerInBetween) {
      return MatrixManipulations.multiplyMatrix(layerInBetween, getSigmoidWeight(errors, outputs));
    }

    private Matrix getSigmoidWeight(Matrix errors, Matrix outputs) {
      return MatrixManipulations.multiplyMatrixElementByElement(errors, outputs,
          getDifferenceWithTargetWeight(outputs));
    }

    private Matrix getDifferenceWithTargetWeight(Matrix outputs) {
      return MatrixManipulations.subtractMatrix(1.0, outputs);
    }

    private void generateLinkWeights() {
      this.weightsInputToHidden = MatrixManipulations.transformArrayToMatrix(this.inputNodes,
          this.hiddenNodes,
          addNegativeNumbersForWeights(generateRandomizedWeights(this.inputNodes, this.hiddenNodes)));
      this.weightsHiddenToOutput = MatrixManipulations.transformArrayToMatrix(this.hiddenNodes,
          this.outputNodes,
          addNegativeNumbersForWeights(generateRandomizedWeights(this.hiddenNodes, this.outputNodes)));
    }

    private double[] addNegativeNumbersForWeights(double[] weights) {
      for (int i = 0; i < weights.length; i++) {
        weights[i] -= 0.5;
      }
      return weights;
    }

    private double[] generateRandomizedWeights(int node1, int node2) {
      return new Random().doubles((long) node1 * node2).toArray();
    }
}
