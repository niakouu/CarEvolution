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
    
    
    private CarAnimations timer;
    private NeuralNetwork neuralNetwork; 
    private Car car; 
    
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
    private ChoiceBox choiceBoxNPL;    
    
    @FXML
    private ChoiceBox choiceBoxLayers;
    
    @FXML
    private Slider sliderAngularVelocity;
    
    @FXML
    private Button btnSaveControls;    
    
    @FXML
    private ChoiceBox choiceBoxMR;    
    
    @FXML
    private Label generationCounter; 
    
    @FXML
    private Label timeCounter; 
    
    
    private Double mutationRate[] = {0.0, 0.05, 0.15, 0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50, 0.55, 0.60, 0.65, 0.70, 0.75, 0.80, 0.85, 0.90, 0.95, 1.00};
    private ObservableList<Double> mutationRateElements = FXCollections.observableArrayList(mutationRate);
    
    private Integer neuronsChoices[] = {0, 1, 2, 3, 4, 5};    
    private ObservableList<Integer> neuronsElements = FXCollections.observableArrayList(neuronsChoices);    
    
    private Integer layersChoices[] = {0, 1, 2, 3, 4};    
    private ObservableList<Integer> layersElements = FXCollections.observableArrayList(layersChoices);    
    
    
    
    @FXML
    void initialize() {
        this.timer = new CarAnimations(root);
        
        this.choiceBoxMR.setItems(mutationRateElements);
        this.choiceBoxMR.setValue(0.7);
        
        this.sliderCarSpeed.setMax(2);
        this.sliderCarSpeed.setMin(0);
        this.sliderCarSpeed.setValue(1);
        this.sliderCarSpeed.setBlockIncrement(0.1f);
        this.sliderCarSpeed.setShowTickLabels(true);
        this.sliderCarSpeed.setShowTickMarks(true);
        this.sliderCarSpeed.setMajorTickUnit(1);
        
        this.choiceBoxNPL.setItems(neuronsElements);
        this.choiceBoxNPL.setValue(6);
        
        this.choiceBoxLayers.setItems(layersElements);
        this.choiceBoxLayers.setValue(3);
        
        this.sliderAngularVelocity.setMax(2);
        this.sliderAngularVelocity.setMin(0);
        this.sliderAngularVelocity.setValue(1);
        this.sliderAngularVelocity.setBlockIncrement(0.1f);
        this.sliderAngularVelocity.setShowTickLabels(true);
        this.sliderAngularVelocity.setShowTickMarks(true);
        this.sliderAngularVelocity.setMajorTickUnit(1);
        
        this.btnStart.setDisable(true);
        this.btnKillCars.setDisable(true);
        this.btnPause.setDisable(true);
        this.btnResetFitnessScore.setDisable(true);
        
        //this.neuralNetwork.setLayers((int)this.choiceBoxLayers.getValue());
        //this.neuralNetwork.setNeurons(neurons);
        //this.car.setAngularVelocity(this.sliderAngularVelocity.getValue());
        //this.car.setVelocity(this.sliderCarSpeed.getValue());
        //this.neuralNetwork.setlearningRate((float)this.choiceBoxMR.getValue());
    }
    
    @FXML
    private void saveControls() {
        this.sliderAngularVelocity.setDisable(true);
        this.sliderCarSpeed.setDisable(true);
        this.choiceBoxLayers.setDisable(true);        
        this.choiceBoxMR.setDisable(true);
        this.choiceBoxNPL.setDisable(true);
        this.btnStart.setDisable(false);   
        
        
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
    private void pauseCars() {
        this.timer.stop();
        this.btnPause.setDisable(true);
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(true);
        this.btnResetFitnessScore.setDisable(false);
        this.btnSetFitnessScore.setDisable(true);
        this.sliderAngularVelocity.setDisable(false);
        this.btnSaveControls.setDisable(false);
        this.sliderCarSpeed.setDisable(false);
        this.choiceBoxLayers.setDisable(false);        
        this.choiceBoxMR.setDisable(false);
        this.choiceBoxNPL.setDisable(false);
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
        this.sliderAngularVelocity.setDisable(true);
        this.btnSaveControls.setDisable(true);
        this.sliderCarSpeed.setDisable(true);
        this.choiceBoxLayers.setDisable(true);        
        this.choiceBoxMR.setDisable(true);
        this.choiceBoxNPL.setDisable(true);
        
    }
    
    
}
