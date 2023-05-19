package com.coursework.predatorpreymodel.application.controllers.parametersInput;

import com.coursework.predatorpreymodel.application.dialogs.AlertBox;
import com.coursework.predatorpreymodel.parameters.EmulationParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.*;

public class ParametersInputField {
    @FXML
    public TextField fieldHeight;
    public TextField fieldWidth;
    public TextField numberOfIndividuals;
    public TextField lifeTacts;

    private Stage stage;
    private Scene scene;

    private final EmulationParameters emulationParameters = EmulationParameters.getInstance();
    public void initializeTextFields() {
        initializeTextField(fieldHeight, emulationParameters.getFieldHeight());
        initializeTextField(fieldWidth, emulationParameters.getFieldWidth());
        initializeTextField(numberOfIndividuals, emulationParameters.getNumberOfIndividuals());
        initializeTextField(lifeTacts, emulationParameters.getLifeTacts());
    }
    private void initializeTextField(TextField field, Integer value) {
        if (value != null)
            field.setText(String.valueOf(value));
    }

    public void Back(ActionEvent event) throws IOException {
        String errorMessage = UpdateEmulationParameters();
        if (errorMessage != "") {
            System.out.println(errorMessage);
            AlertBox.display(errorMessage);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomeScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Next(ActionEvent event) throws IOException {
        String errorMessage = UpdateEmulationParameters();
        if (errorMessage != "") {
            System.out.println(errorMessage);
            AlertBox.display(errorMessage);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ParametersInputSpecies.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/root.css").toExternalForm());

        ParametersInputSpecies parametersInputSpecies = fxmlLoader.getController();
        parametersInputSpecies.initializeTextFields();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private String UpdateEmulationParameters() {
        String errorMessage = checkInputs();
        if (errorMessage.length() != 0)
            return errorMessage;

        emulationParameters.setEmulationParametersField(fieldHeight.getText(), fieldWidth.getText(), numberOfIndividuals.getText(), lifeTacts.getText());
        return "";
    }

    private String checkInputs() {
        String errorMessage;

        errorMessage = CheckInput(fieldHeight.getText(), 1, 50);
        if (errorMessage.length() != 0)
            return "Text field 'field height'" + errorMessage;

        errorMessage = CheckInput(fieldWidth.getText(), 1, 50);
        if (errorMessage.length() != 0)
            return "Text field 'field width'" + errorMessage;

        errorMessage = CheckInput(numberOfIndividuals.getText(), 0, 1000);
        if (errorMessage.length() != 0)
            return "Text field 'Predator max energy'" + errorMessage;

        errorMessage = CheckInput(lifeTacts.getText(), 1, 500);
        if (errorMessage.length() != 0)
            return "Text field 'Predators lifespan'" + errorMessage;

        int height = -1, width = -1;
        if (fieldHeight.getText() != null && fieldHeight.getText().length() != 0)
            height = Integer.parseInt(fieldHeight.getText());
        if (fieldWidth.getText() != null && fieldWidth.getText().length() != 0)
            width = Integer.parseInt(fieldWidth.getText());

        int amount = -1;
        if (numberOfIndividuals.getText() != null && numberOfIndividuals.getText().length() != 0)
            amount = Integer.parseInt(numberOfIndividuals.getText());
        if (amount == -1) {
            if (fieldHeight.getText() == null || fieldHeight.getText().length() == 0)
                fieldHeight.setText("10");
            if (fieldWidth.getText() == null || fieldWidth.getText().length() == 0)
                fieldWidth.setText("10");
            numberOfIndividuals.setText(String.valueOf(Integer.parseInt(fieldHeight.getText()) * Integer.parseInt(fieldWidth.getText()) / 4));
        }
        else {
            if (height == -1 && width == -1) {
                fieldHeight.setText(String.valueOf((int)(ceil(sqrt(amount * 4)))));
                fieldWidth.setText(String.valueOf((int)(ceil(sqrt(amount * 4)))));
            }
            else if (height == -1)
                fieldHeight.setText(String.valueOf(amount * 4 / Integer.parseInt(fieldWidth.getText()) + 1));
            else if (width == -1)
                fieldWidth.setText(String.valueOf(amount * 4 / Integer.parseInt(fieldHeight.getText()) + 1));
        }
        if (fieldWidth.getText() != null && fieldWidth.getText().length() != 0
                && Integer.parseInt(fieldHeight.getText()) * Integer.parseInt(fieldWidth.getText()) < Integer.parseInt(numberOfIndividuals.getText()))
            return "Text field 'Number of individuals' must be less than 'field height' by 'field width'";

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
