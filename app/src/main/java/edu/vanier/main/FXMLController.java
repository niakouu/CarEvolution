/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import edu.vanier.animations.CarAnimations;
import edu.vanier.neuralNetwork.NeuralNetwork;
import edu.vanier.objects.Car;
import edu.vanier.objects.Point;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 *
 * @author enyihou
 */
public class FXMLController {

    private final static int NUMBER_CARS = 20;
    private ArrayList<Car> eliminatedCars;
    private ArrayList<Car> cars;
    private ArrayList<Shape> shapeDangers;
    private ArrayList<Point> fitnessScore;
    private boolean fitnessSet = false;
    private Label time;

    private CarAnimations timer;

    @FXML
    private Pane root;

    @FXML
    private Button btnSetFitnessScore;

    @FXML
    private Button btnResetFitnessScore;

    @FXML
    private Button btnKillCars;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnPause;

    @FXML
    private Slider sliderCarSpeed;

    @FXML
    private Slider sliderNbrOfNeurons;

    @FXML
    private ChoiceBox choiceBoxLayers;

    @FXML
    private Slider sliderAngularVelocity;
    
     @FXML
    private ChoiceBox choiceBoxMR; 

    Double mutationRate[] = {0.0, 0.05, 0.15, 0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50, 0.55, 0.60, 0.65, 0.70, 0.75, 0.80, 0.85, 0.90, 0.95, 1.00};
    ObservableList<Double> mutationRateElements = FXCollections.observableArrayList(mutationRate);
   
    

    @FXML
    void initialize() {
        this.timer = new CarAnimations(root);

        //this.choiceBoxMR.setItems(mutationRateElements);
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(true);
        this.btnPause.setDisable(true);
        this.btnResetFitnessScore.setDisable(true);
    }
    
    @FXML
    private void killCars() {
        this.timer.killAll();
    }

    @FXML
    private void setFitnessScore() {
        this.root.onMouseClickedProperty();
        
        this.btnPause.setDisable(true);
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(true);
        this.btnResetFitnessScore.setDisable(false);
        this.btnSetFitnessScore.setDisable(true);
    }

    @FXML
    private void pauseCars(){
        this.timer.stop();
        this.btnPause.setDisable(true);
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(true);
        this.btnResetFitnessScore.setDisable(false);
        this.btnSetFitnessScore.setDisable(true);
    }

    @FXML
    private void resetFitnessScore() {
        
        
        this.btnPause.setDisable(true);
        this.btnStart.setDisable(true);
        this.btnKillCars.setDisable(true);
        this.btnResetFitnessScore.setDisable(true);
        this.btnSetFitnessScore.setDisable(false);
    }   
    
    @FXML
    private void startCars() {
        this.timer.start();
        
        this.btnPause.setDisable(false);
        this.btnStart.setDisable(true);
        this.btnKillCars.setDisable(false);
        this.btnResetFitnessScore.setDisable(true);
        this.btnSetFitnessScore.setDisable(true);

    }
    
}
