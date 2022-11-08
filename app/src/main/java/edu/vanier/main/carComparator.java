/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import java.util.Comparator;

/**
 *
 * @author enyihou
 */
public class carComparator implements Comparator<Car>{

    @Override
    public int compare(Car o1, Car o2) {
        return o1.fitnessScore-o2.fitnessScore;
        
    }
    
}
