package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ParametersInputField {
    public TextField lifeTacts;
    public TextField fieldHeight;
    public TextField fieldWidth;
    private Stage stage;
    private Scene scene;

    private EmulationParameters emulationParameters;
    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        UpdateEmulationParameters();
        HomeScreen homeScreen = fxmlLoader.getController();
        homeScreen.setEmulationParameters(emulationParameters);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Next(ActionEvent event) throws IOException {
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

    private String UpdateEmulationParameters() {
        emulationParameters = new EmulationParameters(emulationParameters, 0, 0, 0);
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
