package com.coursework.predatorpreymodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Rules {

    private Stage stage;
    private Scene scene;

    public void Back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Start(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParametersInputField.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        ParametersInputField parametersInputField = fxmlLoader.getController();
        parametersInputField.initializeTextFields();

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void Exit(ActionEvent actionEvent) {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
