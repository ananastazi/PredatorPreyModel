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
        String errorName = UpdateEmulationParameters();
        if (errorName != "") {
            System.out.println(errorName);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameEmulation.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        GameEmulation gameEmulation = fxmlLoader.getController();
        gameEmulation.setEmulationParameters(emulationParameters);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameEmulation.GenerateEmulationSystem();
        stage.setScene(scene);
        stage.show();

        gameEmulation.StartEmulation();
    }

    private String UpdateEmulationParameters() {
        /*
numberOfPredators;
predatorsEnergy;
predatorsCriticEnergy
predatorsLifespan;
numberOfPreys;
preysEnergy;
preysCriticEnergy;
preysLifespan;
*/
        String result = CheckInput(numberOfPredators.getText(), 0, 100);
        if (result != "")
            return numberOfPredators.getText() + result;
        emulationParameters = new EmulationParameters(emulationParameters, 0, 0, 0, 0, 0, 0 ,0 ,0);
        return "";
    }

    private String CheckInput(String strNum, Integer min, Integer max) {
        if (strNum == null) {
            return "";
        }
        Integer d;
        try {
            d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return " must be a number in range between " + min + " and " + max;
        }
        if (d < min || d > max)
            return " must be in range between " + min + " and " + max;
        return "";
    }
}
