/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import edu.vanier.animations.CarAnimations;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 *
 * @author enyihou
 */
public class FXMLController {
    
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
    void initialize(){
        this.timer = new CarAnimations(root);
        
        this.btnStart.setDisable(true);
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
        this.btnKillCars.setDisable(true);
        
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
