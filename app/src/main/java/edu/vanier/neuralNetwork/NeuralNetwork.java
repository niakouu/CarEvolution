/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.neuralNetwork;

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
    private Matrix weightsHiddenToOutputs;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, float learningRate) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.learningRate = learningRate;
        generateLinkWeights();
    }

    private void generateLinkWeights() {
        this.weightsInputToHidden = null;
        this.weightsHiddenToOutputs = null;
    }
    
    
}
