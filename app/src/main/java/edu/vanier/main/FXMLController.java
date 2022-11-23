/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import edu.vanier.animations.CarAnimations;
import edu.vanier.neuralNetwork.NeuralNetwork;
import edu.vanier.objects.Car;
import edu.vanier.objects.Point;
import edu.vanier.objects.Sensor;
import java.util.ArrayList;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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
    void initialize(){
        this.timer = new CarAnimations(root);
        
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(true);
    }

    //detect all shapes that represent dangers to the car.
    private ArrayList<Shape> dangers(Pane root) {

        ArrayList<Shape> dangers = new ArrayList<>();
        for (int i = 0; i < root.getChildren().size(); i++) {
            Node node = root.getChildren().get(i);
            if (!Circle.class.isInstance(node) && !Sensor.class.isInstance(node) && Shape.class.isInstance(node)) {
                dangers.add((Shape) root.getChildren().get(i));
            }
        }

        return dangers;
    }
    
    
    @FXML
    private void killCars() {
        this.cars.forEach((car) -> {
            root.getChildren().removeAll(car.getSensors());
            root.getChildren().remove(car);
        });
        
        this.cars.clear();
    }
    
    @FXML
    private void setFitnessScore() {
        this.root.onMouseClickedProperty();
        this.btnStart.setDisable(false);
        this.btnKillCars.setDisable(false);
    }
    
    @FXML
    private void pauseCars(){
      
    }
    @FXML
    private void resetFitnessScore() {
        
    }   
    
    @FXML
    private void startCars() {
        this.timer.start();
    }
    
    private void mutate() {
        eliminatedCars.addAll(cars);
        cars.clear();
        
        Collections.sort(eliminatedCars);
        Car mutator = eliminatedCars.get(eliminatedCars.size() - 1);
        Car secondMutator = eliminatedCars.get(eliminatedCars.size() - 2);
        eliminatedCars.clear();
        
        mutator.setCenterX(65);
        mutator.setCenterY(130);
        
        secondMutator.setCenterX(65);
        secondMutator.setCenterY(130);
        
        cars.add(new Car(root, mutator.getBrain()));
        cars.add(new Car(root, secondMutator.getBrain()));
        
        for (int i = 0; i < NUMBER_CARS - 2; i++) {
            NeuralNetwork brain = (i % 2 == 0 ?
                mutator.getBrain() :
                secondMutator.getBrain()
            ).clone();
            
            brain.mutate();
            
            Car car = new Car(root, brain);
            cars.add(car);
        }
        
        cars.forEach((t) -> t.setRotate(180));
    }
}
