module de.dhbw.presentation {
    requires de.dhbw.domain;
    requires javafx.controls;
    requires javafx.fxml;

    opens de.dhbw.presentation to javafx.fxml;
    exports de.dhbw.presentation;
}