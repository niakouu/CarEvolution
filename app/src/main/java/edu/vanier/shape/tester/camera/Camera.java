/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.shape.tester.camera;

import com.sun.javafx.scene.shape.ShapeHelper;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 *
 * @author enyihou
 */
public class Camera extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 800);
        
        primaryStage.setScene(scene);

        Rectangle rec = new Rectangle(30, 30, Color.BLUE);
        
        Circle ref = new Circle(30, Color.RED);
        ref.setLayoutX(400);
        ref.setLayoutY(60);
        
        rec.setLayoutX(40);
        rec.setLayoutY(40);
        
        
        

        scene.setOnMouseClicked((e) -> {
            
            

            rec.setX(rec.getX() + 30);

            System.out.println(rec.xProperty());
            
            

        });

        scene.setOnKeyPressed((e) -> {

            rec.setRotate(rec.getRotate() +10);

            System.out.println( rec.xProperty());

        });

        root.getChildren().addAll(rec, ref);
        primaryStage.show();
        
        
        PerspectiveCamera cam = new PerspectiveCamera();
        

    }
    
    
    
    

}

class Car extends Rectangle {

    
    DoubleProperty X;
    DoubleProperty Y;

    public Car() {
        super(30, 20, Color.BLUE);

    }

    public Car(double x, double y) {
        super(30, 20, Color.BLUE);
        this.X.setValue(x);
        this.Y.setValue(x);
    }

}

