/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

/**
 *
 * @author 2145013
 */
public class NeuralNetworkHiddenElements {
    
    private final Matrix hiddenInputs;
    private final Matrix hiddenOutputs;
    private final Matrix finalInputs;
    private Matrix finalOutputs;
    private Matrix outputErrors;
    private Matrix hiddenErrors;
    
    

    public NeuralNetworkHiddenElements(Matrix inputs, NeuralNetwork network) {
      this.hiddenInputs = MatrixManipulations.multiplyMatrix(network.getWeightsInputToHidden(), inputs);
      this.hiddenOutputs = MatrixManipulations.sigmoid(hiddenInputs);
      this.finalInputs = MatrixManipulations.multiplyMatrix(network.getWeightsHiddenToOutput(), hiddenOutputs);
      this.finalOutputs = MatrixManipulations.sigmoid(finalInputs);
    }

    public NeuralNetworkHiddenElements(Matrix inputs, Matrix targets, NeuralNetwork network) {
      this.hiddenInputs = MatrixManipulations.multiplyMatrix(inputs,
          network.getWeightsInputToHidden());
      this.hiddenOutputs = MatrixManipulations.sigmoid(hiddenInputs);
      this.finalInputs = MatrixManipulations.multiplyMatrix(hiddenOutputs,
          network.getWeightsHiddenToOutput());
      this.finalOutputs = MatrixManipulations.sigmoid(finalInputs);
      this.finalOutputs = MatrixManipulations.transformArrayToMatrix(
          finalOutputs.getNumberOfColumns(), 1, finalOutputs.getFirstRow());
      this.outputErrors = MatrixManipulations.subtractMatrix(targets, finalOutputs);
      this.hiddenErrors = MatrixManipulations.multiplyMatrix(network.getWeightsHiddenToOutput(),
          outputErrors);
    }

    public Matrix getHiddenOutputs() {
      return this.hiddenOutputs;
    }

    public Matrix getFinalOutputs() {
      return this.finalOutputs;
    }

    public Matrix getOutputErrors() {
      return this.outputErrors;
    }

    public Matrix getHiddenErrors() {
      return this.hiddenErrors;
    }
}
