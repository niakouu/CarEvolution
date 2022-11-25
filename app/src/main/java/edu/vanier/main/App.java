/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.vanier.main;

import edu.vanier.neuralNetwork.NeuralNetworkDisplay;
import edu.vanier.objects.Car;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    //public void start(Stage primaryStage) throws Exception { 
        
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/self_made_map.fxml"));
        //loader.setController(new FXMLController());
        //Scene scene = new Scene(loader.load());
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
        loader.setController(new FXMLController());
        Pane root = loader.load();
        Scene scene = new Scene(root, 1200, 700, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
       
       

        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
