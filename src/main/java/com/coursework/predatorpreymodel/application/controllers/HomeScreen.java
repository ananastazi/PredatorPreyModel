package com.coursework.predatorpreymodel.application.controllers;

import com.coursework.predatorpreymodel.application.controllers.parametersInput.ParametersInputField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class HomeScreen {
    private Stage stage;
    private Scene scene;

    public void Play(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ParametersInputField.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/root.css").toExternalForm());

        ParametersInputField parametersInputField = fxmlLoader.getController();
        parametersInputField.initializeTextFields(); // to initialize textFields if the emulation parameters has values set

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Rules(ActionEvent actionEvent)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Rules.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/root.css").toExternalForm());
        Rules rules = fxmlLoader.getController();
        rules.backButton.setFocusTraversable(false);
        rules.startButton.setFocusTraversable(false);
        rules.exitButton.setFocusTraversable(false);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Exit(ActionEvent actionEvent) {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}