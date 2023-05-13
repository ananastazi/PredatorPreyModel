package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.Emulation;
import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameEmulation {

    public AnchorPane anchorPaneID;
    public GridPane gridPane;
    private Scene scene;
    private Stage stage;

    Emulation emulation;
    private EmulationParameters emulationParameters;
    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }

    public void GenerateEmulationSystem() {
        this.emulation = new Emulation(emulationParameters);
    }

    public void StartEmulation() {
        gridPane.add(new Button("Button"), 1, 1);
        gridPane.add(new Button("Button"), 2, 2);
        gridPane.add(new Button("Button"), 0, 0);
        gridPane.add(new Button("Button"), 0, 1);
    }

    private void UpdateField() {

    }
}
