/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.animations;

import edu.vanier.objects.Car;
import edu.vanier.objects.Point;
import edu.vanier.objects.Sensor;
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class CarAnimations extends AnimationTimer {

    private final static int NUMBER_CARS = 10;
    private int timeCounter;

    private final Label time;
    private final ArrayList<Car> eliminatedCars;
    private final ArrayList<Line> shapeDangers;
    private final Point position;
    private ArrayList<Car> allCars;


    private ArrayList<Car> aliveCars;
    private final ArrayList<Point> fitnessScores;
    private final Pane root;

    public CarAnimations(Pane root, Point position, ArrayList<Line> dangers) {
        this.timeCounter = 0;
        this.position = position;

        //Initializing new objects
        this.time = new Label();
        this.eliminatedCars = new ArrayList<>();
        this.allCars = new ArrayList<>();
        this.fitnessScores = new ArrayList<>();

        //Adding the label to the main Pane
        this.root = root;
        this.root.getChildren().add(time);

        //Components in the map
        this.shapeDangers = dangers;
    }

    @Override
    public void start() {
        super.start();
        this.aliveCars = getNewCars();
    }
    
    @Override
    public void handle(long now) {
        this.timeCounter++;
        this.time.setText(String.valueOf(this.timeCounter));
        for (Car car : aliveCars) {
            car.think();
            car.setFitnessScore(car.getFitnessScore() + 1);

            car.update(this.shapeDangers);
            detectCarCollisionsWithWall(car); 
        }
        
        this.aliveCars.removeIf(Car::isHaveIntersect);

        if (this.aliveCars.isEmpty() || this.timeCounter == 10000) {
            timeCounter = 0;
            mutate();
            eliminatingCarsSensors();
            this.eliminatedCars.clear();
            root.getChildren().removeAll(aliveCars);
            root.getChildren().addAll(aliveCars);
        }

        for (Car car : aliveCars) {
            car.update(this.shapeDangers);
            car.think();
            car.setFitnessScore(car.getFitnessScore() + 1);
            detectCarCollisionsWithWall(car);
        }
        removeDeadCars();

    }

    public void removeDeadCars() {
        this.aliveCars.removeAll(this.eliminatedCars);
    }

    public void killAll() {
        this.aliveCars.forEach((car) -> {
            this.root.getChildren().removeAll(car.getSensors());
            this.root.getChildren().remove(car);
            this.eliminatedCars.add(car);
        });

        this.aliveCars.clear();
    }

    public void setFitnessScores() {
        this.root.setOnMouseClicked((event) -> {
            this.fitnessScores.add(new Point(event.getX(), event.getY()));
        });
    }

    private void mutate() {

        System.out.println(allCars);
        Collections.sort(this.allCars);
                System.out.println(allCars);

        Car mutator = this.allCars.get(this.allCars.size() - 1);
        Car secondMutator = this.allCars.get(this.allCars.size() - 2);

        for (int i = 0; i < allCars.size(); i++) {
            Car currentCar = allCars.get(i);
            if (!currentCar.equals(mutator) && !currentCar.equals(secondMutator)) {

                currentCar.setBrain(i % 2 == 0
                        ? mutator.getBrain()
                        : secondMutator.getBrain());
                currentCar.getBrain().mutate();
                currentCar.setFitnessScore(0);
            }
            currentCar.setFitnessScore(0);
            currentCar.setCenterX(this.position.getLayoutX());
            currentCar.setCenterY(this.position.getLayoutY());
            currentCar.setRotate(180);
        }

    }
    
    /**
     * Creating a new ArrayList of cars.
     *
     * @return an empty ArrayList of Cars
     */
    private ArrayList<Car> getNewCars() {
        ArrayList<Car> newCars = new ArrayList<>();
        for (int i = 0; i < NUMBER_CARS; i++) {
            Car car = new Car(root, this.position.getLayoutX(), this.position.getLayoutY());
            newCars.add(car);
            allCars.add(car);
        }
        newCars.forEach((t) -> t.setRotate(180));
        return newCars;
    }

    private void eliminatingCarsSensors() {
        for (int i = 0; i < this.eliminatedCars.size(); i++) {
            for (Sensor sensor : this.eliminatedCars.get(i).getSensors()) {
                if (!this.root.getChildren().contains(sensor)) {
                    this.root.getChildren().add(sensor);
                }
            }
        }
    }
    
    private void detectCarCollisionsWithWall(Car car) {
        
        for (int j = 0; j < this.shapeDangers.size(); j++) {
            if (Shape.intersect(car, this.shapeDangers.get(j)).getBoundsInParent().getWidth() != -1) {
               
                this.eliminatedCars.add(car);
                for (Sensor sensor : car.getSensors()) {
                    this.root.getChildren().remove(sensor);
                    
                }
            }
        
        }
    }
}
