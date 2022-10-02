/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.shapeTester2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author enyihou
 */
public class Car extends Rectangle {

    double angle = 0;
    double y = 200;
    double x = 200;
    double angleMoveSpeed = 1;
    double XmoveSpeed = 0;
    double YmoveSpeed = 0;
    double acceleration = 0.015;
    double decceleration = 0.005;

    public Car() {
        super(70, 70, Color.TRANSPARENT);
        
        this.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/798.png"))));

        this.setFocusTraversable(true);
        this.setLayoutX(x);
        this.setLayoutY(y);


    }

    public void accelerate() {
        this.XmoveSpeed = this.XmoveSpeed + Math.cos(Math.toRadians(-this.angle)) * this.acceleration;
        this.YmoveSpeed = this.YmoveSpeed + Math.sin(Math.toRadians(-this.angle)) * this.acceleration;

        this.setLayoutX(this.getLayoutX() + this.XmoveSpeed);
        this.setLayoutY(this.getLayoutY() - this.YmoveSpeed);
    }

    public void deccelerate() {
        
     
        

        if (this.XmoveSpeed > decceleration && this.XmoveSpeed>0) {
            this.XmoveSpeed = this.XmoveSpeed - decceleration;
        }
        if (this.XmoveSpeed >= 0 && XmoveSpeed <= decceleration) {
            this.XmoveSpeed = 0;
        }
        
        if (this.XmoveSpeed < decceleration && this.XmoveSpeed<0) {
            this.XmoveSpeed = this.XmoveSpeed + decceleration;
        }
        if (this.XmoveSpeed <= 0 && XmoveSpeed >= decceleration) {
            this.XmoveSpeed = 0;
        }
        
        
        
        
        
        
        
        if (this.YmoveSpeed > decceleration && this.YmoveSpeed>0) {
            this.YmoveSpeed = this.YmoveSpeed - decceleration;
        }
        if (this.YmoveSpeed >= 0 && YmoveSpeed <= decceleration) {
            this.YmoveSpeed = 0;
        }
        
         if (this.YmoveSpeed < decceleration && this.YmoveSpeed<0) {
            this.YmoveSpeed = this.YmoveSpeed + decceleration;
        }
        if (this.YmoveSpeed <= 0 && YmoveSpeed >= decceleration) {
            this.YmoveSpeed = 0;
        }
        
        this.setLayoutX(this.getLayoutX() + this.XmoveSpeed);
        this.setLayoutY(this.getLayoutY() - this.YmoveSpeed);
    }

    public void moveRight() {
        this.angle += angleMoveSpeed;
        this.rotateProperty().set(angle);

      
    }

    public void moveLeft() {
        this.angle -= angleMoveSpeed;

        this.rotateProperty().set(angle);
        System.out.println(angle);
    }

}
