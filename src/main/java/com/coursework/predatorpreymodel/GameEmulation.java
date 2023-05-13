package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.Emulation;
import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import static java.nio.file.Files.getOwner;

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

    public void GenerateEmulationSystem(Stage stage) {
        this.emulation = new Emulation(emulationParameters);

        gridPane = new GridPane();

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

        //gridPane.setMaxWidth(gridPaneSize * width);
        //gridPane.setMaxHeight(gridPaneSize * height);
        gridPane.setLayoutY((800 - height * gridPaneSize) / 2);
        gridPane.setStyle("-fx-grid-lines-visible: true; -fx-border-color: white; -fx-background-color: black;");

        anchorPane = new AnchorPane();
        anchorPane.setBackground(Background.fill(Color.BLACK));
        anchorPane.getChildren().add(gridPane);
        scene = new Scene(anchorPane, 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void RunEmulation() {

        // emulation controller
    }

    private void UpdateField() {

    }
}
