package com.coursework.predatorpreymodel;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;

public class AlertBox {
    public static void display(String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Incorrect input values");
        window.setMinWidth(275);
        window.setMinHeight(200);

        Label errorMessage = new Label();
        errorMessage.setText(message);
        errorMessage.setStyle("src: url('../../../../fonts/Karma-Suture.otf');" +
                              "-fx-font-family: 'Karma Suture';" +
                              "-fx-font-size: 24px;" +
                              "-fx-padding: 20px;");

        Button reenterValuesButton = new Button("Re-enter values");
        reenterValuesButton.setOnAction(e -> window.close());
        reenterValuesButton.setStyle("src: url('../../../../fonts/Karma-Suture.otf');" +
                                     "-fx-font-family: 'Karma Suture';" +
                                     "-fx-font-size: 24px;" +
                                     "-fx-margin: 30px;");

        VBox layout = new VBox(30);
        layout.getChildren().addAll(errorMessage,reenterValuesButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
