package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.Emulation;
import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameEmulation {

    private Scene scene;
    private Stage stage;

    private EmulationParameters emulationParameters;
    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }

    public void GenerateEmulationSystem() {
        Emulation emulation = new Emulation(emulationParameters);
    }

    public void StartEmulation() {

    }
}
