package edu.vanier.shapeTester2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Car car = new Car();
        Pane root = new Pane(car);
        
        Label restart = new Label("Press \"R\" to restart");
        restart.setLayoutX(100);
        restart.setLayoutY(30);
        root.getChildren().add(restart);
        
        
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        
        Controller controller = new Controller(scene, car);

        primaryStage.show();
    }
    

  
}