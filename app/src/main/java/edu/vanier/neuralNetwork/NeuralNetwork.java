/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

import java.util.Arrays;
import java.util.Random;
import edu.vanier.car.Sensor;

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

    public double[] query(Sensor[] data) {
        double[] sensorsData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            sensorsData[i] = data[i].getLength();
        }
        Matrix inputs = getReformedInput(sensorsData);
        Matrix output = getFinalOutput(inputs);
        
        double[] outputs = new double[output.getData()[0].length];
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = output.getData()[0][i];
            System.out.print("[" + outputs[i] + "] , ");
        }
        System.out.println("]");
        return outputs;
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
