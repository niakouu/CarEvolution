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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author edeli
 */
public class CarAnimations extends AnimationTimer {

    private final static int NUMBER_CARS = 10;
    private int counterTime;

    private final Label time;
    private final ArrayList<Car> eliminatedCars;
    private final ArrayList<Car> allCars;
    private final ArrayList<Car> cars;
    private final ArrayList<Shape> shapeDangers;
    private final ArrayList<Point> fitnessScores;
    private final Pane root;

    public CarAnimations(Pane root) {
        this.counterTime = 0;

        //Initializing new objects
        this.time = new Label();
        this.allCars = new ArrayList<>();
        this.eliminatedCars = new ArrayList<>();
        this.fitnessScores = new ArrayList<>();

        //Adding the label to the main Pane
        this.root = root;
        this.root.getChildren().add(time);

        //Components in the map
        this.shapeDangers = dangers();

        //Adding the car to the the arrayList
        this.cars = getNewCars();
        for (Car car : cars) {
            allCars.add(car);
        }
    }

    @Override
    public void handle(long now) {
        this.counterTime++;
        this.time.setText(String.valueOf(this.counterTime));
        
        
       

        for (Car car : cars) {
            car.think();
            
            car.setFitnessScore(car.getFitnessScore() + 1);

            car.update(this.shapeDangers);
             detectCarCollisionsWithWall(car);
        }
       
//        for (Car car : cars){
//            if (car.isHaveIntersect() == true) {
//                cars.remove(car); 
//                break; 
//            }
//        }


        if (this.cars.isEmpty() || this.counterTime == 10000) {
            counterTime = 0;
            mutate();
            eliminatingCarsSensors();
            this.cars.addAll(allCars);
            this.eliminatedCars.clear();
            root.getChildren().removeAll(cars);
            root.getChildren().addAll(cars);
        }

        for (Car car : cars) {
            car.update(this.shapeDangers);
            car.think();
            car.setFitnessScore(car.getFitnessScore() + 1);
            detectCarCollisionsWithWall(car);
        }
        removeDeadCars();

        
        
    }

    public void removeDeadCars() {
        this.cars.removeAll(this.eliminatedCars);
    }

    public void killAll() {
        this.cars.forEach((car) -> {
            this.root.getChildren().removeAll(car.getSensors());
            this.root.getChildren().remove(car);
            this.eliminatedCars.add(car);
        });

        this.cars.clear();
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
            currentCar.setCenterX(65);
            currentCar.setCenterY(130);
            currentCar.setRotate(180);
        }

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
    
    /**
     * Creating a new ArrayList of cars.
     * @return an empty ArrayList of Cars
     */
    private ArrayList<Car> getNewCars() {
        ArrayList<Car> newCars = new ArrayList<>();
        for (int i = 0; i < NUMBER_CARS; i++) {
            Car car = new Car(root);
            newCars.add(car);
        }
        newCars.forEach((t) -> t.setRotate(180));
        return newCars;
    }
    
    private void eliminatingCarsSensors() {
        for (int i = 0; i < this.eliminatedCars.size(); i++) {
            for (Sensor sensor : this.eliminatedCars.get(i).getSensors()) {
                if(!this.root.getChildren().contains(sensor))
                this.root.getChildren().add(sensor);
            }
        }
    }
    
    private void detectCarCollisionsWithWall(Car car) {
        
        for (int j = 0; j < this.shapeDangers.size(); j++) {
            if (Shape.intersect(car, this.shapeDangers.get(j)).getBoundsInParent().getWidth() != -1) {
               //this.cars.remove(car);
               //car.stop();
               //car.setHaveIntersect(true);
                this.eliminatedCars.add(car);
                for (Sensor sensor : car.getSensors()) {
                    this.root.getChildren().remove(sensor);
                }
               // this.root.getChildren().remove(car);
            
            }
        }
    }

    public ArrayList<Point> getFitnessScores() {
        return this.fitnessScores;
    }
    
     public int getTimeCounter() {
        return counterTime;
    }

    public void setTimeCounter(int counterTime) {
        this.counterTime = counterTime;
    }
}


