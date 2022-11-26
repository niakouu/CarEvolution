package edu.vanier.neuralNetwork;

import edu.vanier.objects.Sensor;

public final class NeuralNetwork {

    private int layers;
    private int[] size;
    private Weight[][][] weights;
    private Neuron[][] neurons;
    private float learningRate;

    public NeuralNetwork(int[] size, float learningRate) {
        this.learningRate = learningRate;
        this.layers = size.length;
        this.size = size;
        this.weights = new Weight[layers - 1][][];
        for (int i = 1; i < layers; i++) {
            weights[i - 1] = new Weight[size[i]][size[i - 1]];
        }

        neurons = new Neuron[layers][];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron[this.size[i]];
            for (int j = 0; j < neurons[i].length; j++) {
                if (i == 0) {
                    neurons[i][j] = new Neuron(true);
                } else {
                    neurons[i][j] = new Neuron();
                }
            }
        }
        this.generateRandomWeight();
    }

    public double[] calculate(Sensor[] sensors) {
        double[] inputs = new double[sensors.length];

        for (int i = 0; i < sensors.length; i++) {
            inputs[i] = sensors[i].getProjectedLength().doubleValue();
        }

        for (int i = 0; i < this.neurons[0].length; i++) {
            this.neurons[0][i].setValue(inputs[i]);
        }
        for (int i = 1; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i].length; j++) {
                Neuron neuron = neurons[i][j];
                neuron.calculate(weights[i - 1][j], neurons[i - 1]);
            }
        }
        return this.outputs();
    }

    public void mutate() {

        if (Math.random()<this.learningRate) {
            for (Weight[][] weight : weights) {
                for (Weight[] weight1 : weight) {
                    for (Weight weight11 : weight1) {
                        weight11.setValue((Math.random() - 0.5) * 2);
                    }
                }
            }
        }

    }

    public void generateRandomWeight() {

        for (int i = 1; i < neurons.length; i++) {

            for (int j = 0; j < neurons[i].length; j++) {

                Neuron currentNeuron = neurons[i][j];

                for (int k = 0; k < neurons[i - 1].length; k++) {

                    Neuron previousNeuron = neurons[i - 1][k];

                    Weight weight = new Weight(previousNeuron, currentNeuron);
                    weight.startXProperty().bind(previousNeuron.layoutXProperty());
                    weight.startYProperty().bind(previousNeuron.layoutYProperty());

                    weight.endXProperty().bind(currentNeuron.layoutXProperty());
                    weight.endYProperty().bind(currentNeuron.layoutYProperty());
                    weight.setValue(2 * (Math.random() - 0.5));

                    weights[i - 1][j][k] = weight;

                }

            }
        }

    }

    public double[] outputs() {
        double[] answers = new double[neurons[neurons.length - 1].length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = neurons[neurons.length - 1][i].activationFunctionValue();
        }
        return answers;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public int[] getSize() {
        return size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }

    public Weight[][][] getWeights() {
        return weights;
    }

    public Neuron[][] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[][] neurons) {
        this.neurons = neurons;
    }
    
    public void setlearningRate(float learningRate){
        this.learningRate = learningRate; 
    }
}
