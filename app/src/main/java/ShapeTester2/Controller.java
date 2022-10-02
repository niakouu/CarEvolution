/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShapeTester2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 *
 * @author enyihou
 */
public class Controller {

    Scene scene;
    Car car;

    boolean wPressed = false, aPressed = false, dPressed = false;

    public Controller(Scene scene, Car car) {
        this.scene = scene;
        this.car = car;

        listenKey();
        animation.start();

    }

    AnimationTimer animation = new AnimationTimer() {
        @Override
        public void handle(long now) {

                car.deccelerate();
            

            if (wPressed) {
                car.accelerate();
            }

            if (aPressed) {
                car.moveLeft();
            }

            if (dPressed) {
                car.moveRight();
            }
        }

    };

    public void listenKey() {
      

        scene.setOnKeyPressed((event) -> {

            if (event.getCode() == KeyCode.W) {
                wPressed = true;
            }
            if (event.getCode() == KeyCode.A) {
                aPressed = true;
            }

            if (event.getCode() == KeyCode.D) {
                dPressed = true;
            }

        });

        scene.setOnKeyReleased((event) -> {
            
            if (event.getCode() == KeyCode.R) {

                System.out.println("Released");
                car.setLayoutX(200);
                car.setLayoutY(200);
            }

            if (event.getCode() == KeyCode.W) {
                wPressed = false;
                System.out.println("W pressed");
            }
            if (event.getCode() == KeyCode.A) {
                aPressed = false;
            }

            if (event.getCode() == KeyCode.D) {
                dPressed = false;
            }

        });

    }

}
