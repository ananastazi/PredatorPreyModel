package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.EmulationParameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class HomeScreen {
    private Stage stage;
    private Scene scene;
    private EmulationParameters emulationParameters;

    public void setEmulationParameters(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
    }

    public void Play(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParametersInputField.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        emulationParameters = new EmulationParameters(emulationParameters, 0, 0, 0);
        ParametersInputField parametersInputField = fxmlLoader.getController();
        parametersInputField.setEmulationParameters(emulationParameters);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Rules(ActionEvent event)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rules.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/Rules.css").toExternalForm());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}