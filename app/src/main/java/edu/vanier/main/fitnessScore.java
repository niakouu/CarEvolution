/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.main; 
import edu.vanier.car.Car;
import java.util.HashMap;
import javafx.scene.shape.Shape;

/**
 *
 * @author nguye
 */
public class FitnessScore {
    HashMap <Car,Shape> findCar;
    

    public FitnessScore() {
        this.findCar = new HashMap<>();
    }
}
