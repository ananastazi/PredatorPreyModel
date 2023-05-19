module com.coursework.predatorpreymodel {
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.coursework.predatorpreymodel.application.controllers to javafx.fxml;
    opens com.coursework.predatorpreymodel.application.controllers.parametersInput to javafx.fxml;
    opens com.coursework.predatorpreymodel.application.dialogs to javafx.fxml;
    opens com.coursework.predatorpreymodel.domain.cell to javafx.fxml;
    opens com.coursework.predatorpreymodel.domain.individuals to javafx.fxml;
    opens com.coursework.predatorpreymodel.domain.interfaces to javafx.fxml;
    opens com.coursework.predatorpreymodel.infrastructure to javafx.fxml;
    opens com.coursework.predatorpreymodel.parameters to javafx.fxml;
    opens com.coursework.predatorpreymodel.utils to javafx.fxml;

    exports com.coursework.predatorpreymodel.application;
    exports com.coursework.predatorpreymodel.domain.cell;
    exports com.coursework.predatorpreymodel.domain.individuals;
    exports com.coursework.predatorpreymodel.domain.interfaces;
    exports com.coursework.predatorpreymodel.infrastructure;
    exports com.coursework.predatorpreymodel.parameters;
    exports com.coursework.predatorpreymodel.utils;
//
//
//    opens com.coursework.predatorpreymodel to javafx.fxml;
//    exports com.coursework.predatorpreymodel.application.сontrollers;
//    opens com.coursework.predatorpreymodel.application.сontrollers to javafx.fxml;
//    exports com.coursework.predatorpreymodel;
//    exports com.coursework.predatorpreymodel.application.dialogs;
//    opens com.coursework.predatorpreymodel.application.dialogs to javafx.fxml;
//    exports com.coursework.predatorpreymodel.application.сontrollers.parametersInput;
//    opens com.coursework.predatorpreymodel.application.сontrollers.parametersInput to javafx.fxml;
//    exports com.coursework.predatorpreymodel.infrastructure.Generators;
//    opens com.coursework.predatorpreymodel.infrastructure.Generators to javafx.fxml;
//    exports com.coursework.predatorpreymodel.parameters;
//    opens com.coursework.predatorpreymodel.parameters to javafx.fxml;
//    exports com.coursework.predatorpreymodel.domain.interfaces;
//    opens com.coursework.predatorpreymodel.domain.interfaces to javafx.fxml;
//    exports com.coursework.predatorpreymodel.infrastructure;
//    opens com.coursework.predatorpreymodel.infrastructure to javafx.fxml;
//    exports com.coursework.predatorpreymodel.domain.cell;
//    opens com.coursework.predatorpreymodel.domain.cell to javafx.fxml;
//    exports com.coursework.predatorpreymodel.domain.cell.typesOfCell;
//    opens com.coursework.predatorpreymodel.domain.cell.typesOfCell to javafx.fxml;
//    exports com.coursework.predatorpreymodel.domain.individuals;
//    opens com.coursework.predatorpreymodel.domain.individuals to javafx.fxml;
//    exports main;
//    opens main to javafx.fxml;
//    exports com.coursework.predatorpreymodel.application;
//    opens com.coursework.predatorpreymodel.application to javafx.fxml;
//    exports com.coursework.predatorpreymodel.utils;
//    opens com.coursework.predatorpreymodel.utils to javafx.fxml;
//    exports com.coursework.predatorpreymodel;
}