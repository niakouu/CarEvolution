/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author enyihou
 */
public class FXMLController {

    @FXML
    void initialize() {
        
        btnPause.setOnAction((e) -> {
            App.timer.stop();

        });
        btnPlay.setOnAction((e) -> {
            App.timer.start();
        });
        
    }
    
    @FXML
    Button btnPause;
    @FXML
    Button btnPlay;

}
