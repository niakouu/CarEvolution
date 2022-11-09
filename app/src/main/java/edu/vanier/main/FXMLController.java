/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import edu.vanier.neuralNetwork.NeuralNetwork;
import edu.vanier.objects.Car;
import edu.vanier.objects.Sensor;
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    
    @FXML
    private Pane root;
    
    @FXML
    void initialize(){
        Label time = new Label();
        root.getChildren().add(time);

        //Components in the map
        eliminatedCars = new ArrayList<>();
        shapeDangers = dangers(root);
        cars = new ArrayList<>();

        for (int i = 0; i < NUMBER_CARS; i++) {
            Car car = new Car(root);
            cars.add(car);
        }
        
        cars.forEach((t) -> t.setRotate(180));
        
        //Behaviors at each frame.

        AnimationTimer timer = new AnimationTimer() {
            
            private int timeCounter = 0;
            private long cycles = 0;
            private static long maxCycles = 1000;
            
            @Override
            public void handle(long now) {
                timeCounter++;

                time.setText(String.valueOf(timeCounter));

                if (cars.isEmpty() || timeCounter == 10000) {
                    
                    mutate();

                    for (int i = 0; i < eliminatedCars.size(); i++) {
                        for (Sensor sensor : eliminatedCars.get(i).getSensors()) {

                            if(!root.getChildren().contains(sensor))
                            root.getChildren().add(sensor);
                        }
                    }
                    eliminatedCars.clear();
                }

                for (int i = 0; i < cars.size(); i++) {
                    Car car = cars.get(i);
                    car.think();
                    car.setFitnessScore(car.getFitnessScore() + 1);

                    //detect collision
                    for (int j = 0; j < shapeDangers.size(); j++) {
                        if (Shape.intersect(car, shapeDangers.get(j)).getBoundsInParent().getWidth() != -1) {
                            cars.remove(car);
                            eliminatedCars.add(car);
                            for (int k = 0; k < car.getSensors().length; k++) {
                                root.getChildren().remove(car.getSensors()[k]);
                            }
                            root.getChildren().remove(car);
                        }
                    }
                    if (this.cycles++ >= maxCycles && car.getMove() == 0) {
                        cars.remove(car);
                        maxCycles++;
                        car.stop();
                        for (int k = 0; k < car.getSensors().length; k++) {
                            root.getChildren().remove(car.getSensors()[k]);
                        }
                        root.getChildren().remove(car);
                        eliminatedCars.add(car);
                    }
                    car.update(shapeDangers);
                }
            }
        };
        timer.start();
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
    private void killall() {
        this.cars.forEach((car) -> {
            root.getChildren().removeAll(car.getSensors());
            root.getChildren().remove(car);
        });
        
        this.cars.clear();
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
