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
import javafx.scene.control.Alert;
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
    private Button setLayers;

    @FXML
    private Slider sliderCarSpeed;

    @FXML
    private Slider sliderAngularVelocity;

    @FXML
    private Button btnSaveControls;

    @FXML
    private Slider sliderMutationRate;

    @FXML
    private ChoiceBox<Integer> choiceBoxNC;

    private Integer[] nbOfCars = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private ObservableList<Integer> nbOfCarsElements = FXCollections.observableArrayList(nbOfCars);

    @FXML
    void initialize() {
        this.timer = new CarAnimations(root);

        this.choiceBoxNC.setItems(nbOfCarsElements);
        this.choiceBoxNC.setValue(10);

        this.sliderCarSpeed.setMax(5);
        this.sliderCarSpeed.setMin(0.5);
        this.sliderCarSpeed.setValue(1);
        this.sliderCarSpeed.setBlockIncrement(0.1f);
        this.sliderCarSpeed.setShowTickLabels(true);
        this.sliderCarSpeed.setShowTickMarks(true);
        this.sliderCarSpeed.setMajorTickUnit(1);

        this.sliderAngularVelocity.setMax(5);
        this.sliderAngularVelocity.setMin(0);
        this.sliderAngularVelocity.setValue(1);
        this.sliderAngularVelocity.setBlockIncrement(0.1f);
        this.sliderAngularVelocity.setShowTickLabels(true);
        this.sliderAngularVelocity.setShowTickMarks(true);
        this.sliderAngularVelocity.setMajorTickUnit(1);

        this.sliderMutationRate.setMax(1);
        this.sliderMutationRate.setMin(0);
        this.sliderMutationRate.setValue(1);
        this.sliderMutationRate.setBlockIncrement(0.1f);
        this.sliderMutationRate.setShowTickLabels(true);
        this.sliderMutationRate.setShowTickMarks(true);
        this.sliderMutationRate.setMajorTickUnit(1);
        this.sliderMutationRate.setValue(0.7);

        this.btnStart.setDisable(true);
        this.btnKillCars.setDisable(true);
        this.btnPause.setDisable(true);
        this.btnResetFitnessScore.setDisable(true);

        this.btnSaveControls.setOnAction((e) -> {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText("All progress will be removed");
            alert.setContentText("Please confirm the current progress will be removed to apply the changes");

            alert.setOnCloseRequest((event) -> {
                saveControls();
            });
            alert.show();

        });

    }

    @FXML
    private void saveControls() {
        this.btnStart.setDisable(false);

        Car.setLEARNING_RATE((float) sliderMutationRate.getValue());
        Car.setMAX_ANGULAR_VELOCITY(sliderAngularVelocity.getValue());
        Car.setMAX_VELOCITY(sliderCarSpeed.getValue());
        CarAnimations.setNUMBER_CARS((int) choiceBoxNC.getValue());

        this.timer.restart();

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
    private void openSetLayers() {
        
        SetLayers setLayersStage = new SetLayers();
        setLayersStage.show();
        
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
        this.sliderMutationRate.setDisable(false);
        this.choiceBoxNC.setDisable(false);
        this.setLayers.setDisable(false);

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
        this.sliderMutationRate.setDisable(true);
        this.choiceBoxNC.setDisable(true);
        this.setLayers.setDisable(true);

    }

}
