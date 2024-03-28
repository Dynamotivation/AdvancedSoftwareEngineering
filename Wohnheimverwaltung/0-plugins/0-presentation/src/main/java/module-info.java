module de.dhbw.presentation {
    requires de.dhbw.domain;
    requires javafx.controls;
    requires javafx.fxml;

    opens de.dhbw.plugins.presentation to javafx.fxml;
    exports de.dhbw.plugins.presentation;
}