package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.min;

public class GameEmulation {
    public AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    public GridPane gridPane;
    Emulation emulation;
    private EmulationParameters emulationParameters;

    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }

    public void GenerateEmulationSystem() {
        this.emulation = new Emulation(emulationParameters);

    }

    public void RunEmulation() {

        // operating emulation tacts/generations here
    }

    public void GenerateScene(Stage stage) {
        this.stage = stage; // PROBABLY NOT NECESSARY
        gridPane = new GridPane();
// MUST BE CONNECTED TO EmulationParameters instance
        Integer width = 10;
        Integer height = 10;
        double gridPaneSize = min(900.0 / width, 800.0 / height);

        for (int i = 0; i < width; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(gridPaneSize);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < height; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(gridPaneSize);
            gridPane.getRowConstraints().add(rowConst);
        }

        gridPane.setLayoutY((800 - height * gridPaneSize) / 2);
        gridPane.setStyle("-fx-grid-lines-visible: true; -fx-border-color: white; -fx-background-color: black;");

        anchorPane = new AnchorPane();
        anchorPane.setBackground(Background.fill(Color.BLACK));
        anchorPane.getChildren().add(gridPane);
        scene = new Scene(anchorPane, 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    private void UpdateScene() {
        PlayField playField = emulation.getPlayField();
        for (int i = 0; i < playField.getHeight(); ++i) {
            for (int j = 0; j < playField.getWidth(); ++j) {
                Cell cell = playField.getField().get(i).get(j);
                switch (cell.getTypeOfCell()) {
                    case 0:
                        // set ground Image to pane
                        break;
                    case 1:
                        // set grass Image to pane
                        break;
                    default:
                        break;
                }

                if (cell.getIndividual().getClass() == Predator.class) {
                    // set image inside the pane
                }
                if (cell.getIndividual().getClass() == Prey.class) {
                    // set image inside the pane
                }
            }
        }
    }
}
