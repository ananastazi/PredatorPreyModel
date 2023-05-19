package com.coursework.predatorpreymodel.application.controllers;

import com.coursework.predatorpreymodel.infrastructure.Emulation;
import com.coursework.predatorpreymodel.infrastructure.PlayField;
import com.coursework.predatorpreymodel.infrastructure.QueueOfChanges;
import com.coursework.predatorpreymodel.utils.Position;
import com.coursework.predatorpreymodel.domain.individuals.Predator;
import com.coursework.predatorpreymodel.domain.individuals.Prey;
import com.coursework.predatorpreymodel.parameters.EmulationParameters;
import eu.hansolo.toolbox.tuples.Pair;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

public class GameEmulation {
    private Scene scene;
    private Stage stage;
    // region Nodes connected fields
    public AnchorPane anchorPane; double windowWidth = 1280.0, windowHeight = 800.0;
    private GridPane gridPane; private double gridPaneSize;
    AnchorPane rightPanel;
    private Button pauseButton;
    private Button homeButton;
    private Slider speedSlider;
    private DoubleProperty durationMultiplier = new SimpleDoubleProperty(1.0);
    private Label tactLabel;

    // endregion
    private Timeline executionTimeline;
    private PauseTransition pauseTransition;
    // region lists of cells
    private final List<Image> cellsImg = new ArrayList<>(Arrays.asList(new Image("file:src/main/resources/media/ground.jpg"), new Image("file:src/main/resources/media/grassBlock.jpg"))); // Images "ground.png" and "grass.png"
    private List<List<ImageView>> cellsForForegroundImages;
    // 0 - ground; 1 - grass;
    private final List<Image> speciesImg = new ArrayList<>(Arrays.asList(new Image("file:src/main/resources/media/fox.png"), new Image("file:src/main/resources/media/rabbit.png"))); // Images "fox.png" and "rabbit.png"
    private List<List<ImageView>> cellsForIndividuals; // predator/prey will be put inside
    // 0 - fox; 1 - rabbit;
    // endregion
    // region Emulation fields
    private final EmulationParameters emulationParameters = EmulationParameters.getInstance();
    private Emulation emulation;
    // endregion
    private Integer currentTact;

    public GameEmulation(Stage stage) {
        this.stage = stage;
        this.emulation = new Emulation();
        this.emulation.prepareEmulation();
        generateScene();
        prepareScene();
        this.currentTact = 1;
    }

    // region buttons onAction
    public void openHomeScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomeScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void toggleButton() {
        String btnName = pauseButton.getText();
        switch (btnName) {
            case "Start" -> {
                executeNextTact();
                pauseButton.setText("Pause");
            }
            case "Pause" -> {
                executionTimeline.pause();
                pauseTransition = new PauseTransition(Duration.seconds(0.2));
                pauseTransition.setOnFinished(event -> {
                    pauseButton.setText("Resume");
                    pauseButton.setDisable(false);
                });
                pauseButton.setDisable(true);
                pauseTransition.play(); // to prevent the user to accidentally click the button twice
            }
            case "Resume" -> {
                executionTimeline.play();
                pauseTransition.stop();
                pauseButton.setText("Pause");
            }
            case "Restart" -> {
                this.emulation = new Emulation();
                this.emulation.prepareEmulation();
                generateScene();
                prepareScene();
                this.currentTact = 1;
                pauseButton.setText("Start");
            }
        }
    }
    // endregion

    private void executeNextTact() {
        if (currentTact <= emulationParameters.getLifeTacts()) {
            emulation.performTact();
            Duration duration = Duration.seconds(0.01);
            executionTimeline = new Timeline(new KeyFrame(duration, executionEvent -> {
                if (currentTact > emulationParameters.getLifeTacts()) {
                    System.out.println("IT QUITED THE EXECUTION TIMELINE WITH QueueOfChanges " + QueueOfChanges.getInstance().size());
                    return;
                }
                if (!QueueOfChanges.getInstance().isEmpty()) {
                    Duration updatingDuration = Duration.seconds(1).multiply(durationMultiplier.get());
                    Timeline updatingTimeline = new Timeline(new KeyFrame(updatingDuration, updatingEvent -> {
                        if (currentTact > emulationParameters.getLifeTacts()) {
                            System.out.println("IT QUITED THE EXECUTION TIMELINE. ERROR upd");
                            executionTimeline.stop();
                            return;
                        }
                        if (pauseButton.getText() == "Pause" && (pauseTransition == null || pauseTransition.getStatus() != Animation.Status.RUNNING)) {// if the game is still on
                            updateSceneOnce();
                            executionTimeline.play();
                        }
                    }));
                    updatingTimeline.play(); // timeline to show one change (record) with duration automatically updated with the slider
                    executionTimeline.pause(); // to continue after the updatingTimeline finish its work
                } else {
                    currentTact++;
                    if (currentTact > emulationParameters.getLifeTacts()) {
                        tactLabel.setText("Last tact: " + (currentTact - 1));
                        pauseButton.setText("Restart");
                        executionTimeline.stop();
                        return;
                    }
                    if (true) { // if the autoplay is off
                        toggleButton(); // Set pause the execution and make button "Resume"
                    }
                    tactLabel.setText("Tact: " + currentTact);
                    executeNextTact();
                }
            }));
            executionTimeline.setCycleCount(Animation.INDEFINITE);
            executionTimeline.play();
        }
    }
    private void generateScene() {
        generateGridPane();
        generateRightPanel();

        anchorPane = new AnchorPane(generateContainer()); // the container is a grid with the field and the right panel of nodes.
        anchorPane.setBackground(Background.fill(Color.BLACK));

        scene = new Scene(anchorPane, 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/GameEmulation.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/stylesheets/root.css").toExternalForm()); // to import standard font
        this.stage.setScene(scene);
        this.stage.show();
    }

    private GridPane generateContainer() {
        GridPane container = new GridPane();
        container.getColumnConstraints().addAll(
                new ColumnConstraints(min(windowWidth * 0.75, 900.0)),
                new ColumnConstraints(windowWidth - min(windowWidth * 0.75, 900.0))
        );

        container.add(new AnchorPane(gridPane), 0, 0);
        container.add(rightPanel, 1, 0);
        return container;
    }

    private void generateGridPane() {
        Integer height = emulation.getPlayField().getHeight();
        Integer width = emulation.getPlayField().getWidth();
        gridPaneSize = min(min(windowWidth * 0.75, 900.0) / width, windowHeight / height);

        gridPane = new GridPane();
        gridPane.setLayoutX((min(windowWidth * 0.75, 900.0) - width * gridPaneSize) / 2);
        gridPane.setLayoutY((windowHeight - height * gridPaneSize) / 2);
        gridPane.getStyleClass().add("playfield");

        for (int i = 0; i < width; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(gridPaneSize));
        }
        for (int j = 0; j < height; j++) {
            gridPane.getRowConstraints().add(new RowConstraints(gridPaneSize));
        }
    }

    private void generateRightPanel() {
        pauseButton = new Button("Start");
        pauseButton.setOnAction(e -> { toggleButton(); });
        pauseButton.setPrefWidth(300);
        pauseButton.setPrefHeight(80);
        pauseButton.setLayoutX((windowWidth - min(windowWidth * 0.75, 900.0)) / 2 - 150);
        pauseButton.setLayoutY(windowHeight * 0.6);
        pauseButton.setCursor(Cursor.HAND);
        pauseButton.getStyleClass().add("game-button");

        homeButton = new Button("Home");
        homeButton.setOnAction(e -> {
            try {
                openHomeScreen(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        homeButton.setPrefWidth(300);
        homeButton.setPrefHeight(80);
        homeButton.setLayoutX((windowWidth - min(windowWidth * 0.75, 900.0)) / 2 - 150);
        homeButton.setLayoutY(windowHeight * 0.6 + 100);
        homeButton.setCursor(Cursor.HAND);
        homeButton.getStyleClass().add("game-button");

        speedSlider = new Slider(0.1, 3, 1);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            durationMultiplier.set(newValue.doubleValue());
        });
        speedSlider.setPrefWidth(300);
        speedSlider.setLayoutX((windowWidth - min(windowWidth * 0.75, 900.0)) / 2 - 150);
        speedSlider.setLayoutY(windowHeight * 0.6 - 100);
        speedSlider.getStyleClass().add("speed-slider");

        tactLabel = new Label("Cycle: 1");
        tactLabel.setPrefWidth(300);
        tactLabel.setTextAlignment(TextAlignment.CENTER);
        tactLabel.setLayoutX((windowWidth - min(windowWidth * 0.75, 900.0)) / 2 - 150);
        tactLabel.setLayoutY(windowHeight * 0.6 - 200);
        tactLabel.getStyleClass().add("tact-label");

        rightPanel = new AnchorPane(pauseButton, homeButton, speedSlider, tactLabel);
    }

    private void prepareScene() {
        // adding ground to cellsForForegroundImages based on the List emulation.getGrass()
        // initialize cellsForSpecies and cellsForForegroundImages (list of lists of imageView) and put each one in the gridPane

        this.cellsForForegroundImages = new ArrayList<>();
        this.cellsForIndividuals = new ArrayList<>();
        for (int i = 0; i < emulation.getPlayField().getHeight(); ++i) {
            List<ImageView> innerList1 = new ArrayList<>();
            List<ImageView> innerList2 = new ArrayList<>();
            for (int j = 0; j < emulation.getPlayField().getWidth(); ++j) {
                switch (PlayField.getInstance().getField().get(i).get(j).getTypeOfCell()) {
                    case 1:
                        innerList1.add(new ImageView(cellsImg.get(1))); // setting grass
                        break;
                    default:
                        innerList1.add(new ImageView()); // setting empty new imageView
                        break;
                }
                innerList1.get(j).setFitWidth(gridPaneSize);
                innerList1.get(j).setFitHeight(gridPaneSize);
                gridPane.add(innerList1.get(j), j, i); // adding to the gridPane
                // must write j, i because: j - is the col; i - is the row;

                innerList2.add(new ImageView()); // setting empty new imageView (will be refilled with individuals)
                innerList2.get(j).setFitWidth(gridPaneSize);
                innerList2.get(j).setFitHeight(gridPaneSize);
                gridPane.add(innerList2.get(j), j, i); // adding to the gridPane
                // must write j, i because: j - is the col; i - is the row;
            }
            this.cellsForForegroundImages.add(innerList1);
            this.cellsForIndividuals.add(innerList2);
        }
        while(!QueueOfChanges.getInstance().isEmpty())
            updateSceneOnce();
    }

    private void updateSceneOnce() {
        Pair<Integer, Pair<Position, Position>> recordOfChange = QueueOfChanges.getInstance().pop();
        if (recordOfChange.getA() == 0) {
            if (recordOfChange.getB().getA() != null)
                cellsForForegroundImages.get(recordOfChange.getB().getA().getX()).get(recordOfChange.getB().getA().getY()).setOpacity(0);
            if (recordOfChange.getB().getB() != null) {
                if (PlayField.getInstance().getCell(recordOfChange.getB().getB()).getTypeOfCell() == 0)
                    cellsForForegroundImages.get(recordOfChange.getB().getB().getX()).get(recordOfChange.getB().getB().getY()).setOpacity(0);
                if (PlayField.getInstance().getCell(recordOfChange.getB().getB()).getTypeOfCell() == 1)
                    cellsForForegroundImages.get(recordOfChange.getB().getB().getX()).get(recordOfChange.getB().getB().getY()).setOpacity(1);
            }
        }
        if (recordOfChange.getA() == 1) {
            if (recordOfChange.getB().getA() != null)
                cellsForIndividuals.get(recordOfChange.getB().getA().getX()).get(recordOfChange.getB().getA().getY()).setImage(null);
            if (recordOfChange.getB().getB() != null && PlayField.getInstance().getCell(recordOfChange.getB().getB()).getIndividual() != null) {
                Image image = null;
                if (PlayField.getInstance().getCell(recordOfChange.getB().getB()).getIndividual().getClass() == Predator.class)
                    image = speciesImg.get(0);
                if (PlayField.getInstance().getCell(recordOfChange.getB().getB()).getIndividual().getClass() == Prey.class)
                    image = speciesImg.get(1);
                cellsForIndividuals.get(recordOfChange.getB().getB().getX()).get(recordOfChange.getB().getB().getY()).setImage(image);
            }
        }
    }
}
