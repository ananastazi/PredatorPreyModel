package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class GameEmulation {
    public AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;
    private final List<Image> cellsImg = new ArrayList<>(2); // create Image "ground.png" and "grass.png"
    private List<List<ImageView>> cellsForForegroundImages;
    private final List<Image> speciesImg = new ArrayList<>(2); // create Image "fox.png" and "rabbit.png"
    private List<List<ImageView>> cellsForIndividuals; // predator/prey will be put inside
    Emulation emulation;
    private final EmulationParameters emulationParameters = EmulationParameters.getInstance();

    public void GenerateEmulationSystem() {
        this.emulation = new Emulation();
    }

    public void RunEmulation() {
        this.emulation.prepareEmulation();
        prepareScene();
        for (int i = 0; i < emulationParameters.getLifeTacts(); i++) {
            emulation.performTact();
            UpdateScene();
        }
    }

    public void GenerateScene(Stage stage) {
        this.stage = stage; // PROBABLY NOT NECESSARY
        gridPane = new GridPane();
// MUST BE CONNECTED TO EmulationParameters instance
        Integer height = emulationParameters.getFieldHeight();
        Integer width = emulationParameters.getFieldWidth();
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

        // for height
        //        for width
        // initialize cellsForSpecies and cellsForForegroundImages (list of lists of imageView) and put each one in the gridPane
        // here you can put imageView with ground

        gridPane.setLayoutY((800 - height * gridPaneSize) / 2);
        gridPane.setStyle("-fx-grid-lines-visible: true; -fx-border-color: white; -fx-background-color: black;");

        anchorPane = new AnchorPane();
        anchorPane.setBackground(Background.fill(Color.BLACK));
        anchorPane.getChildren().add(gridPane);
        scene = new Scene(anchorPane, 1280, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void prepareScene() {
        // adding grass to cellsForForegroundImages based on the List emulation.getGrass()
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
