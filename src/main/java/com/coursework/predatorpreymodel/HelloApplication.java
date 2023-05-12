package com.coursework.predatorpreymodel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheets/root.css").toExternalForm());

        stage.setResizable(false);

        stage.setTitle("Predator VS Prey");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        start(new Stage());
    }
}