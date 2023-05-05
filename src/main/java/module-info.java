module com.coursework.predatorpreymodel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.coursework.predatorpreymodel to javafx.fxml;
    exports com.coursework.predatorpreymodel;
}