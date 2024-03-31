module de.dhbw.plugins.presentation {
    // Import other layers
    requires de.dhbw.plugin.persistence;
    requires de.dhbw.application;
    requires de.dhbw.domain;

    // Import JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Export to JavaFX
    opens de.dhbw.plugins.presentation to javafx.fxml;
    exports de.dhbw.plugins.presentation to javafx.graphics;
}