package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

public class GameEmulation {
    public AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;
    private final List<Image> cellsImg = new ArrayList<>(Arrays.asList(new Image("file:src/main/resources/media/ground.jpg"), new Image("file:src/main/resources/media/grassBlock.jpg"))); // Images "ground.png" and "grass.png"
    private List<List<ImageView>> cellsForForegroundImages;
    private final List<Image> speciesImg = new ArrayList<>(Arrays.asList(new Image("file:src/main/resources/media/fox.png"), new Image("file:src/main/resources/media/rabbit.png"))); // Images "fox.png" and "rabbit.png"
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

        gridPane.setLayoutY((800 - height * gridPaneSize) / 2);
        gridPane.setStyle(
                "-fx-grid-lines-visible: true; " +
                        "-fx-border-color: white; " +
                        "-fx-background-color: white;" +
                        "-fx-background-image: url('media/ground.jpg')");

        anchorPane = new AnchorPane();
        anchorPane.setBackground(Background.fill(Color.BLACK));

        for (int i = 0; i < width; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(gridPaneSize);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int j = 0; j < height; j++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(gridPaneSize);
            gridPane.getRowConstraints().add(rowConst);
        }

        // initialize cellsForSpecies and cellsForForegroundImages (list of lists of imageView) and put each one in the gridPane

        this.cellsForForegroundImages = new ArrayList<>();
        this.cellsForIndividuals = new ArrayList<>();
        for (int i = 0; i < width; ++i) {
            List<ImageView> innerList1 = new ArrayList<>();
            List<ImageView> innerList2 = new ArrayList<>();
            for (int j = 0; j < height; ++j) {
                ImageView groundView = new ImageView(cellsImg.get(0));
                groundView.setFitWidth(gridPaneSize);
                groundView.setFitHeight(gridPaneSize);
                gridPane.add(groundView, i, j);

                innerList1.add(new ImageView());
                innerList1.get(j).setFitWidth(gridPaneSize);
                innerList1.get(j).setFitHeight(gridPaneSize);
                gridPane.add(innerList1.get(j), i, j);

                innerList2.add(new ImageView());
                innerList2.get(j).setFitWidth(gridPaneSize);
                innerList2.get(j).setFitHeight(gridPaneSize);
                gridPane.add(innerList2.get(j), i, j);
            }
            this.cellsForForegroundImages.add(innerList1);
            this.cellsForIndividuals.add(innerList2);
        }

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
