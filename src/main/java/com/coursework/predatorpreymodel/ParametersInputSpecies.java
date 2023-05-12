package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ParametersInputSpecies {

    @FXML
    public TextField numberOfPredators;
    public TextField predatorsEnergy;
    public TextField predatorsCriticEnergy;
    public TextField predatorsLifespan;
    public TextField numberOfPreys;
    public TextField preysEnergy;
    public TextField preysCriticEnergy;
    public TextField preysLifespan;
    public TextField lifeTacts;

    private Scene scene;
    private Stage stage;

    private EmulationParameters emulationParameters;
    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParametersInputField.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        UpdateEmulationParameters();
        ParametersInputField parametersInputField = fxmlLoader.getController();
        parametersInputField.setEmulationParameters(emulationParameters);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Start(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParametersInputSpecies.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        UpdateEmulationParameters();
        ParametersInputSpecies parametersInputSpecies = fxmlLoader.getController();
        parametersInputSpecies.setEmulationParameters(emulationParameters);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void UpdateEmulationParameters() {
        emulationParameters = new EmulationParameters(emulationParameters, 0, 0, 0, 0, 0, 0 ,0 ,0);
    }
}
