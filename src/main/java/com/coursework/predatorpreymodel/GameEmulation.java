package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.*;
import eu.hansolo.toolbox.tuples.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

public class GameEmulation {
    public AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private GridPane gridPane; private double gridPaneSize;
    // 0 - ground; 1 - grass;
    private final List<Image> cellsImg = new ArrayList<>(Arrays.asList(new Image("file:src/main/resources/media/ground.jpg"), new Image("file:src/main/resources/media/grassBlock.jpg"))); // Images "ground.png" and "grass.png"
    private List<List<ImageView>> cellsForForegroundImages;
    // 0 - fox; 1 - rabbit;
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
        PlayField.getInstance().printPlayField();
        for (int i = 0; i < emulationParameters.getLifeTacts(); i++) {
            emulation.performTact();
            PlayField.getInstance().printPlayField();
            updateScene(3000);
        }
    }

    public void GenerateScene(Stage stage) {
        this.stage = stage; // PROBABLY NOT NECESSARY
        gridPane = new GridPane();
// MUST BE CONNECTED TO EmulationParameters instance
        Integer height = emulationParameters.getFieldHeight();
        Integer width = emulationParameters.getFieldWidth();
        gridPaneSize = min(900.0 / width, 800.0 / height);

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

        anchorPane.getChildren().add(gridPane);

        scene = new Scene(anchorPane, 1280, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void prepareScene() {
        // adding ground to cellsForForegroundImages based on the List emulation.getGrass()
        // initialize cellsForSpecies and cellsForForegroundImages (list of lists of imageView) and put each one in the gridPane

        this.cellsForForegroundImages = new ArrayList<>();
        this.cellsForIndividuals = new ArrayList<>();
        for (int i = 0; i < PlayField.getInstance().getHeight(); ++i) {
            List<ImageView> innerList1 = new ArrayList<>();
            List<ImageView> innerList2 = new ArrayList<>();
            for (int j = 0; j < PlayField.getInstance().getWidth(); ++j) {
                ImageView groundView = new ImageView(cellsImg.get(0));
                groundView.setFitWidth(gridPaneSize);
                groundView.setFitHeight(gridPaneSize);
                gridPane.add(groundView, i, j);
                if (PlayField.getInstance().getField().get(i).get(j).getTypeOfCell() == 1)
                    innerList1.add(new ImageView(cellsImg.get(1))); // setting grass
                else
                    innerList1.add(new ImageView()); // setting something else

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
        updateScene(10000);
    }

    private void updateScene(Integer sleep) {
        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1000), event -> { System.out.println("finished"); })
        );

        int qSize = QueueOfChanges.getInstance().size();
        for (int i = 0; i < qSize; i++) {
//            timer.play();
//            System.out.println("started");
            updateSceneOnce();
        }
        AlertBox.display("...");
    }

    private void updateSceneOnce() {
        QueueOfChanges queueOfChanges = QueueOfChanges.getInstance();
            Pair<Integer, Pair<Position, Position>> changePair = queueOfChanges.pop();
//            System.out.println(changePair.getA()
//                    + " " + changePair.getB().getA().getX() + " " + changePair.getB().getA().getY() + ";"
//                    + " " + changePair.getB().getB().getX() + " " + changePair.getB().getB().getY());
            if (changePair.getA() == 0) {
                if (changePair.getB().getA() != null)
                    cellsForForegroundImages.get(changePair.getB().getA().getX()).get(changePair.getB().getA().getY()).setOpacity(0);
                if (changePair.getB().getB() != null) {
                    if (PlayField.getInstance().getCell(changePair.getB().getB()).getTypeOfCell() == 0)
                        cellsForForegroundImages.get(changePair.getB().getB().getX()).get(changePair.getB().getB().getY()).setOpacity(0);
                    if (PlayField.getInstance().getCell(changePair.getB().getB()).getTypeOfCell() == 1)
                        cellsForForegroundImages.get(changePair.getB().getB().getX()).get(changePair.getB().getB().getY()).setOpacity(1);
                }
            }
            if (changePair.getA() == 1) {
                if (changePair.getB().getA() != null)
                    cellsForIndividuals.get(changePair.getB().getA().getX()).get(changePair.getB().getA().getY()).setImage(null);
                if (changePair.getB().getB() != null && PlayField.getInstance().getCell(changePair.getB().getB()).getIndividual() != null) {
                    Image image = null;
                    if (PlayField.getInstance().getCell(changePair.getB().getB()).getIndividual().getClass() == Predator.class)
                        image = speciesImg.get(0);
                    if (PlayField.getInstance().getCell(changePair.getB().getB()).getIndividual().getClass() == Prey.class)
                        image = speciesImg.get(1);
                    cellsForIndividuals.get(changePair.getB().getB().getX()).get(changePair.getB().getB().getY()).setImage(image);
            }
        }
    }
}
