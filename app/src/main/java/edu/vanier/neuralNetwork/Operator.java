/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package edu.vanier.neuralNetwork;

/**
 *
 * @author 2145013
 */
public enum Operator {
    
    ADDITION("+"){
        @Override
        public double apply(double x1, double x2) {
            return x1 + x2;
        }
        
    },
    SUBSTRACTION("-"){
        @Override
        public double apply(double x1, double x2) {
            return x1 - x2;
        }
    },
    MULTIPLICATION("*"){
        @Override
        public double apply(double x1, double x2) {
            return x1 * x2;
        }
    };
    
    private final String text;
    
    Operator(String text){
        this.text = text;
    }
    
    public abstract double apply(double x1, double x2);
    
    @Override
    public String toString(){
        return text;
    }
}
