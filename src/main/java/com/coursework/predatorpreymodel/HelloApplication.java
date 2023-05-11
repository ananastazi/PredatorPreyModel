package com.coursework.predatorpreymodel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);

        stage.setMaxHeight(800);
        stage.setMinHeight(800);
        stage.setMaxWidth(1280);
        stage.setMinWidth(1280);

        stage.setTitle("Predator VS Prey!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        start(new Stage());
    }
}