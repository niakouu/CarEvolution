/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neuralNetwork;

/**
 *
 * @author enyihou
 */
public class NeuralNetworkManipulation {

    public static void Mutate(NeuralNetwork mutatedNetwork, NeuralNetwork mutatorNetwork, int fitnessScore1, NeuralNetwork SecondMutatorNetwork, int fitnessScore2) {

        double[][] Wio1 = mutatedNetwork.WinputToHidden;
        double[][] Wio2 = mutatorNetwork.WinputToHidden;
        double[][] Wio3 = SecondMutatorNetwork.WinputToHidden;
        for (int i = 0; i < Wio1.length; i++) {
            for (int j = 0; j < Wio1[i].length; j++) {
                int totalFitnessScore = fitnessScore1 + fitnessScore2;
                double average = (fitnessScore1 / totalFitnessScore) * Wio2[i][j] + (fitnessScore2 / totalFitnessScore) * Wio3[i][j];
                double difference = average - Wio1[i][j];
                double change = mutatedNetwork.learningRate * difference;

                double delta = (Wio1[i][j] + change) * (Math.random() - 0.5) * 0.2;

                Wio1[i][j] += change + delta;

            }
        }

        Wio1 = mutatedNetwork.WhiddenToOutput;
        Wio2 = mutatorNetwork.WhiddenToOutput;
        Wio3 = SecondMutatorNetwork.WhiddenToOutput;
        for (int i = 0; i < Wio1.length; i++) {
            for (int j = 0; j < Wio1[i].length; j++) {
                int totalFitnessScore = fitnessScore1 + fitnessScore2;
                double average = (fitnessScore1 / totalFitnessScore) * Wio2[i][j] + (fitnessScore2 / totalFitnessScore) * Wio3[i][j];
                double difference = average - Wio1[i][j];
                double change = mutatedNetwork.learningRate * difference;

                double delta = (Wio1[i][j] + change) * (Math.random() - 0.5) * 0.2;

                Wio1[i][j] += change + delta;

            }
        }

    }

}
