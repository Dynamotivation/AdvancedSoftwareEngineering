module de.dhbw.presentation {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.dhbw.presentation to javafx.fxml;
    exports de.dhbw.presentation;
}