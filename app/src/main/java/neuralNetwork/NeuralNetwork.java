/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neuralNetwork;

import edu.vanier.main.Car;
import java.util.Arrays;

/**
 *
 * @author enyihou
 */
public class NeuralNetwork {

    int nbInodes;
    int nbHnodes;
    int nbOnodes;
    double learningRate;

    double[] inputNodes;
    double[] outputNodes;
    double[] hiddenNodes;

    double[][] WinputToHidden;
    double[][] WhiddenToOutput;

    public NeuralNetwork(int inodes, int hiddenLayers, int onodes, double learningRate) {

        this.nbInodes = inodes;
        this.nbHnodes = hiddenLayers;
        this.nbOnodes = onodes;
        this.learningRate = learningRate;

        this.inputNodes = new double[nbInodes];
        this.outputNodes = new double[nbOnodes];

        //to be changed
        this.hiddenNodes = new double[nbHnodes];

        this.WinputToHidden = new double[nbHnodes][nbInodes];
        this.WhiddenToOutput = new double[nbOnodes][nbHnodes];

        for (int i = 0; i < WinputToHidden.length; i++) {
            for (int j = 0; j < WinputToHidden[i].length; j++) {
                WinputToHidden[i][j] = Math.random() - 0.5d;
            }
        }

        for (int i = 0; i < WhiddenToOutput.length; i++) {
            for (int j = 0; j < WhiddenToOutput[i].length; j++) {
                WhiddenToOutput[i][j] = Math.random() - 0.5d;
            }
        }

    }

    public double[] calculate(double[] inputs) {

        for (int j = 0; j < WinputToHidden.length; j++) {
            double num = 0.0;

            for (int k = 0; k < inputs.length; k++) {
                num = num + WinputToHidden[j][k] * (inputs[k]);
            }

            hiddenNodes[j] = num;
        }
        
        for (int j = 0; j < WhiddenToOutput.length; j++) {
            double num = 0.0;

            for (int k = 0; k < hiddenNodes.length; k++) {
                num = num + WhiddenToOutput[j][k] * (hiddenNodes[k]);
            }

            outputNodes[j] = (sigmoid(num)-0.5)*Car.angularVelocity*Car.speed;
        }
        
        return this.outputNodes;

    }

    public static void main(String[] args) {
    }
    
    public static double sigmoid(double num){
        return 1/(1+Math.exp(-num));
    }
    
    private static double Relu(double num){
        return Math.max(0, num);
    }

    private static void printArray(double[][] array) {
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + ", ");
            }
            System.out.println("");
        }
    }
}
