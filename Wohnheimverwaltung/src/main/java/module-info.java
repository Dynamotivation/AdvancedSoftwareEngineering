module de.dhbw {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.dhbw.b_presentation to javafx.fxml;
    exports de.dhbw.b_presentation;
}