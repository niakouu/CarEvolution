/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import edu.vanier.core.neural.network.Neuron;
import edu.vanier.core.neural.network.Weight;
import edu.vanier.objects.Car;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author enyihou
 */
public class SetLayers extends Stage {

    private static final int PADDING = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final VBox root = new VBox();
    private final Scene scene = new Scene(root);
    private final ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
    private final IntegerProperty nbOfLayer = new SimpleIntegerProperty();
    private final ArrayList<IntegerProperty> size = new ArrayList<>();

    public SetLayers() {
        setOriginal();
        this.setTitle("Set Hidden Layers");
        this.setX(500);
        this.setY(300);
        this.setMinHeight(HEIGHT);
        this.setMinWidth(WIDTH);
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);

        root.getChildren().addAll(numberOfLayers(),
                generateControlsForNeurons(),
                generateUI());

    }

    private HBox numberOfLayers() {

        Label lb = new Label("Number of Hidden Layers : ");
        HBox hBox = new HBox(lb);
        hBox.setAlignment(Pos.CENTER);
        Button button = new Button("-");
        Label label = new Label(String.valueOf("\t" + network.size()) + " \t");
        Button button1 = new Button("+");

        button.setOnAction((e) -> {
            network.remove(network.size() - 2);
            size.remove(network.size() - 1);

            label.setText(String.valueOf("\t" + network.size()) + " \t");
            nbOfLayer.set(nbOfLayer.get() - 1);
            repaint();
            checkButton(button, button1);

        });

        button1.setOnAction((e) -> {
            ArrayList<Neuron> al = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                al.add(new Neuron());
            }
            nbOfLayer.set(nbOfLayer.get() + 1);
            network.add(network.size() - 1, al);
            size.add(network.size() - 1, new SimpleIntegerProperty(3));
            label.setText(String.valueOf("\t" + network.size()) + " \t");

            repaint();
            checkButton(button, button1);

        });

        hBox.getChildren().addAll(button, label, button1);

        return hBox;

    }

    private Pane generateUI() {

        Pane pane = new Pane();
        pane.setBackground(Background.fill(Color.DARKGREY));
        pane.prefWidthProperty().bind(this.widthProperty());
        pane.prefHeightProperty().bind(this.heightProperty().subtract(50));

        generateNeurons(pane);
        generateWeights(pane);

        return pane;

    }

    private void setOriginal() {

        for (int i = 0; i < Car.getLAYERS().length; i++) {
            ArrayList<Neuron> al = new ArrayList<>();
            for (int j = 0; j < Car.getLAYERS()[i]; j++) {
                al.add(new Neuron());
            }
            network.add(al);
            size.add(new SimpleIntegerProperty(Car.getLAYERS()[i]));

        }
        System.out.println(size);
        nbOfLayer.set(Car.getLAYERS().length);

    }

    private Pane generateControlsForNeurons() {

        Pane pane = new Pane();
        pane.setPrefHeight(100);
        for (int i = 0; i < network.size(); i++) {

            if (i != 0 && i != network.size() - 1) {

                HBox control = new HBox();
                control.setAlignment(Pos.CENTER);
                Button buttonMinusNeuron = new Button("-");

                Label label = new Label();
                label.textProperty().bind(size.get(i).asString());
                Button buttonPlusNeuron = new Button("+");
                int index = i;
                buttonMinusNeuron.setOnAction((e) -> {

                    System.out.println(buttonMinusNeuron.isDisable());
                    network.get(index).remove(network.get(index).size() - 1);
                    size.get(index).set(size.get(index).get() - 1);
                    repaint();
                    checkSecondaryButton(buttonMinusNeuron, buttonPlusNeuron, index);

                });

                buttonPlusNeuron.setOnAction((e) -> {

                    network.get(index).add(network.get(index).size(), new Neuron());

                    size.get(index).set(size.get(index).get() + 1);
                    repaint();
                    checkSecondaryButton(buttonMinusNeuron, buttonPlusNeuron, index);

                });
                control.getChildren().addAll(buttonMinusNeuron, label, buttonPlusNeuron);
                control.layoutXProperty().bind((((this.widthProperty().subtract(PADDING * 2)).divide(nbOfLayer.add(1))).multiply(i + 1)).subtract(PADDING + 10));
                control.setLayoutY(10);
                pane.getChildren().add(control);

            }

        }
        return pane;
    }

    private void generateWeights(Pane pane) {

        for (int i = 0; i < network.size() - 1; i++) {
            for (int j = 0; j < network.get(i).size(); j++) {
                for (int k = 0; k < network.get(i + 1).size(); k++) {

                    Weight weight = new Weight();
                    weight.bindStart(network.get(i).get(j));
                    weight.bindEnd(network.get(i + 1).get(k));

                    pane.getChildren().add(weight);
                    weight.toBack();

                }

            }
        }

    }

    private void generateNeurons(Pane pane) {

        for (int i = 0; i < network.size(); i++) {

            for (int j = 0; j < network.get(i).size(); j++) {

                Neuron neuron = network.get(i).get(j);
                neuron.layoutXProperty().bind((((this.widthProperty().subtract(PADDING * 2)).divide(nbOfLayer.add(1))).multiply(i + 1)).add(PADDING));
                neuron.layoutYProperty().bind((((this.heightProperty().subtract(PADDING * 2)).divide(size.get(i).add(1))).multiply(j + 1)));

                pane.getChildren().add(neuron);
                neuron.toFront();
            }

        }

    }

    private void checkButton(Button button, Button button1) {

        if (network.size() == 2) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }

        if (network.size() == 5) {
            button1.setDisable(true);
        } else {
            button1.setDisable(false);
        }
    }

    private void checkSecondaryButton(Button button, Button button1, int index) {

        if (network.get(index).size() == 1) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }

        if (network.get(index).size() == 10) {
            button1.setDisable(true);
        } else {
            button1.setDisable(false);
        }
    }

    private void repaint() {

        root.getChildren().remove(root.getChildren().size() - 1);
        root.getChildren().add(generateUI());
    }
}
