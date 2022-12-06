package edu.vanier.shape.tester.collisions;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Shape;

public class Controller implements Initializable {

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    private int movementVariable = 2;

    private int counter = 0;

    @FXML
    private Rectangle shape1;

    @FXML
    private Rectangle shape2;

    @FXML
    private CubicCurve curve1;

    @FXML
    private CubicCurve curve2;

    @FXML
    private AnchorPane scene;

    ArrayList<Shape> dangers = new ArrayList<>();

    @FXML
    void start(ActionEvent event) {
        shape1.setLayoutY(50);
        shape1.setLayoutX(20);
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {

            if (wPressed.get()) {

                shape1.setLayoutY(shape1.getLayoutY() - movementVariable);
            }

            if (sPressed.get()) {
                shape1.setLayoutY(shape1.getLayoutY() + movementVariable);
            }

            if (aPressed.get()) {
                shape1.setLayoutX(shape1.getLayoutX() - movementVariable);
            }

            if (dPressed.get()) {
                shape1.setLayoutX(shape1.getLayoutX() + movementVariable);
            }

            boolean isTouching = false;
            for (int i = 0; i < dangers.size() && isTouching == false; i++) {

                if ((Shape.intersect(shape1, dangers.get(i))).getBoundsInLocal().getWidth() != -1) {
                    isTouching = true;
                }

            }

            if (isTouching) {

                counter++;
                System.out.println(counter);
                if (wPressed.get()) {

                    shape1.setLayoutY(shape1.getLayoutY() + movementVariable);
                }

                if (sPressed.get()) {
                    shape1.setLayoutY(shape1.getLayoutY() - movementVariable);
                }

                if (aPressed.get()) {
                    shape1.setLayoutX(shape1.getLayoutX() + movementVariable);
                }

                if (dPressed.get()) {
                    shape1.setLayoutX(shape1.getLayoutX() - movementVariable);
                }

            }

        }

    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movementSetup();

        for (int i = 0; i < scene.getChildren().size(); i++) {

            if (scene.getChildren().get(i) != shape1 && !(Button.class.isInstance(scene.getChildren().get(i)))) {
                dangers.add((Shape) scene.getChildren().get(i));
            }

        }

        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        }));

    }

    public void movementSetup() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
    }
}
