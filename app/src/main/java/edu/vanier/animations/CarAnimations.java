/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.animations;

import edu.vanier.neuralNetwork.NeuralNetwork;
import edu.vanier.objects.Car;
import edu.vanier.objects.Point;
import edu.vanier.objects.Sensor;
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class CarAnimations extends AnimationTimer{

    private final static int NUMBER_CARS = 20;
    private static long maxCycles = 1000;
    private int timeCounter;
    private long cycles;
    
    private Label time;
    private ArrayList<Car> eliminatedCars;
    private ArrayList<Car> cars;
    private ArrayList<Shape> shapeDangers;
    private ArrayList<Point> fitnessScore;
    private Pane root;

    public CarAnimations(Pane root) {
        this.timeCounter = 0;
        this.cycles = 0;
        
        this.time = new Label();
        this.eliminatedCars = new ArrayList<>();
        this.root = root;
        this.root.getChildren().add(time);
        
        //Components in the map
        this.shapeDangers = dangers();
        
        this.cars = new ArrayList<>();
        for (int i = 0; i < NUMBER_CARS; i++) {
            Car car = new Car(root);
            this.cars.add(car);
        }
        this.cars.forEach((t) -> t.setRotate(180));
    }

    @Override
    public void handle(long now) {
        this.timeCounter++;
        this.time.setText(String.valueOf(this.timeCounter));

        if (this.cars.isEmpty() || this.timeCounter == 10000) {

            mutate();

            for (int i = 0; i < this.eliminatedCars.size(); i++) {
                for (Sensor sensor : this.eliminatedCars.get(i).getSensors()) {

                    if(!this.root.getChildren().contains(sensor))
                    this.root.getChildren().add(sensor);
                }
            }
            this.eliminatedCars.clear();
        }

        for (int i = 0; i < this.cars.size(); i++) {
            Car car = this.cars.get(i);
            car.think();
            car.setFitnessScore(car.getFitnessScore() + 1);

            //detect collision
            for (int j = 0; j < this.shapeDangers.size(); j++) {
                if (Shape.intersect(car, this.shapeDangers.get(j)).getBoundsInParent().getWidth() != -1) {
                    this.cars.remove(car);
                    this.eliminatedCars.add(car);
                    for (int k = 0; k < car.getSensors().length; k++) {
                        this.root.getChildren().remove(car.getSensors()[k]);
                    }
                    this.root.getChildren().remove(car);
                }
            }
            if (this.cycles++ >= maxCycles && car.getMove() == 0) {
                this.cars.remove(car);
                maxCycles++;
                car.stop();
                for (int k = 0; k < car.getSensors().length; k++) {
                    this.root.getChildren().remove(car.getSensors()[k]);
                }
                this.root.getChildren().remove(car);
                this.eliminatedCars.add(car);
            }
            car.update(this.shapeDangers);
        }
    }
    
    public void killAll() {
        this.cars.forEach((car) -> {
            root.getChildren().removeAll(car.getSensors());
            root.getChildren().remove(car);
        });
        
        this.cars.clear();
    }
    
    private void mutate() {
        this.eliminatedCars.addAll(this.cars);
        this.cars.clear();
        
        Collections.sort(this.eliminatedCars);
        Car mutator = this.eliminatedCars.get(this.eliminatedCars.size() - 1);
        Car secondMutator = this.eliminatedCars.get(this.eliminatedCars.size() - 2);
        this.eliminatedCars.clear();
        
        mutator.setCenterX(65);
        mutator.setCenterY(130);
        
        secondMutator.setCenterX(65);
        secondMutator.setCenterY(130);
        
        this.cars.add(new Car(this.root, mutator.getBrain()));
        this.cars.add(new Car(this.root, secondMutator.getBrain()));
        
        for (int i = 0; i < NUMBER_CARS - 2; i++) {
            NeuralNetwork brain = (i % 2 == 0 ?
                mutator.getBrain() :
                secondMutator.getBrain()
            ).clone();
            
            brain.mutate();
            
            Car car = new Car(this.root, brain);
            this.cars.add(car);
        }
        
        this.cars.forEach((t) -> t.setRotate(180));
    }
    
    //detect all shapes that represent dangers to the car.
    private ArrayList<Shape> dangers() {
        ArrayList<Shape> dangers = new ArrayList<>();
        for (int i = 0; i < this.root.getChildren().size(); i++) {
            Node node = this.root.getChildren().get(i);
            if (!Circle.class.isInstance(node) && !Sensor.class.isInstance(node) && Shape.class.isInstance(node)) {
                dangers.add((Shape) this.root.getChildren().get(i));
            }
        }
        return dangers;
    }
    
}
