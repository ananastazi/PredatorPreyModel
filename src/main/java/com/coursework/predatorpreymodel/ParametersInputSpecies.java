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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.unsignedMultiplyHigh;

public class ParametersInputSpecies {
    @FXML
    public TextField numberOfPredators;
    public TextField predatorsMaxEnergy;
    public TextField predatorsCriticEnergy;
    public TextField predatorsLifespan;
    public TextField numberOfPreys;
    public TextField preysMaxEnergy;
    public TextField preysCriticEnergy;
    public TextField preysLifespan;

    private Scene scene;
    private Stage stage;

    EmulationParameters emulationParameters = EmulationParameters.getInstance();
    public void initializeTextFields() {
        initializeTextField(numberOfPredators, emulationParameters.getNumberOfPredators());
        initializeTextField(predatorsMaxEnergy, emulationParameters.getPredatorsMaxEnergy());
        initializeTextField(predatorsCriticEnergy, emulationParameters.getPredatorsCriticalEnergy());
        initializeTextField(predatorsLifespan, emulationParameters.getPredatorsLifespan());
        initializeTextField(numberOfPreys, emulationParameters.getNumberOfPreys());
        initializeTextField(preysMaxEnergy, emulationParameters.getPreysMaxEnergy());
        initializeTextField(preysCriticEnergy, emulationParameters.getPreysCriticalEnergy());
        initializeTextField(preysLifespan, emulationParameters.getPreysLifespan());
    }
    private void initializeTextField(TextField field, Integer value) {
        if (value != null)
            field.setText(String.valueOf(value));
    }
    public void Back(ActionEvent event) throws IOException {
        String errorMessage = UpdateEmulationParameters();
        if (errorMessage != "") {
            System.out.println(errorMessage);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParametersInputField.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        ParametersInputField parametersInputField = fxmlLoader.getController();
        parametersInputField.initializeTextFields();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Start(ActionEvent event) throws IOException {
        String errorMessage = UpdateEmulationParameters();
        if (errorMessage.length() != 0) {
            System.out.println(errorMessage);
            return;
        }

        GameEmulation gameEmulation = new GameEmulation();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        gameEmulation.GenerateEmulationSystem();
        gameEmulation.GenerateScene(stage);
        gameEmulation.RunEmulation();
    }

    private String UpdateEmulationParameters() {
        String errorMessage = checkInputs();
        if (errorMessage.length() != 0)
            return errorMessage;
        emulationParameters.setEmulationParametersSpecies(numberOfPredators.getText(), predatorsMaxEnergy.getText(), predatorsCriticEnergy.getText(), predatorsLifespan.getText(), numberOfPreys.getText(), preysMaxEnergy.getText(), preysCriticEnergy.getText(), preysLifespan.getText());
        return "";
    }

    private String checkInputs() {
        String errorMessage;

        errorMessage = CheckInput(numberOfPredators.getText(), 0, emulationParameters.getNumberOfIndividuals());
        if (errorMessage.length() != 0)
            return "Text field 'Number of predators'" + errorMessage;

        errorMessage = CheckInput(predatorsCriticEnergy.getText(), 0, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predator critical energy'" + errorMessage;

        errorMessage = CheckInput(predatorsMaxEnergy.getText(), 0, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predator max energy'" + errorMessage;

        errorMessage = CheckInput(predatorsLifespan.getText(), 1, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predators lifespan'" + errorMessage;

        errorMessage = CheckInput(numberOfPreys.getText(), 0, emulationParameters.getNumberOfIndividuals());
        if (errorMessage.length() != 0)
            return "Text field 'Number of predators'" + errorMessage;

        errorMessage = CheckInput(preysCriticEnergy.getText(), 0, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predator critical energy'" + errorMessage;

        errorMessage = CheckInput(preysMaxEnergy.getText(), 0, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predator max energy'" + errorMessage;

        errorMessage = CheckInput(preysLifespan.getText(), 1, 100);
        if (errorMessage.length() != 0)
            return "Text field 'Predators lifespan'" + errorMessage;

        if (numberOfPredators.getText() != null &&
                numberOfPredators.getText().length() != 0 &&
                numberOfPreys.getText() != null &&
                numberOfPreys.getText().length() != 0 &&
                Integer.parseInt(numberOfPredators.getText()) + Integer.parseInt(numberOfPreys.getText()) > emulationParameters.getNumberOfIndividuals())
            return "Text fields 'Number of predators' and 'Number of preys' must be less than 'Number of Individuals'";

        if ((numberOfPredators.getText() == null ||
                numberOfPredators.getText().length() == 0) &&
                numberOfPreys.getText() != null &&
                numberOfPreys.getText().length() != 0)
                numberOfPredators.setText(String.valueOf(
                        min(
                                Integer.parseInt(numberOfPreys.getText()) / 2,
                                (emulationParameters.getNumberOfIndividuals() - Integer.parseInt(numberOfPreys.getText()))) / 2
                ));
        if (numberOfPredators.getText() != null &&
                numberOfPredators.getText().length() != 0 &&
                (numberOfPreys.getText() == null ||
                numberOfPreys.getText().length() == 0))
            numberOfPredators.setText(String.valueOf(
                    min(
                            Integer.parseInt(numberOfPredators.getText()) * 2,
                            (emulationParameters.getNumberOfIndividuals() - Integer.parseInt(numberOfPredators.getText()))) / 2
            ));


        if (predatorsMaxEnergy.getText() != null &&
                predatorsMaxEnergy.getText().length() != 0 &&
                predatorsCriticEnergy.getText() != null &&
                predatorsCriticEnergy.getText().length() != 0 &&
                Integer.parseInt(predatorsCriticEnergy.getText()) > Integer.parseInt(predatorsMaxEnergy.getText()))
            return "Text field 'Predator max energy' must be bigger than text field 'Predator critical energy'";
        if (preysMaxEnergy.getText() != null &&
                preysMaxEnergy.getText().length() != 0 &&
                preysCriticEnergy.getText() != null &&
                preysCriticEnergy.getText().length() != 0 &&
                Integer.parseInt(preysCriticEnergy.getText()) > Integer.parseInt(preysMaxEnergy.getText()))
            return "Text field 'Predator max energy' must be bigger than text field 'Predator critical energy'";

        return "";
    }

    private String CheckInput(String strNum, Integer min, Integer max) {
        if (strNum == null || strNum.length() == 0) {
            return "";
        }
        int d;
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
