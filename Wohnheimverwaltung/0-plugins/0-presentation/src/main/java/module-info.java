module de.dhbw.plugin.presentation {
    // Import other layers
    requires de.dhbw.plugin.persistence;
    requires de.dhbw.application;
    requires de.dhbw.domain;

    // Import JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material;
    requires jfxflexbox;
    requires flexbox;
    requires java.compiler;

    // Export to JavaFX
    exports de.dhbw.plugin.presentation to javafx.graphics;
    opens de.dhbw.plugin.presentation to javafx.fxml;
    exports de.dhbw.plugin.presentation.startView to javafx.graphics;
    opens de.dhbw.plugin.presentation.startView to javafx.fxml;
    exports de.dhbw.plugin.presentation.overviewView to javafx.graphics;
    opens de.dhbw.plugin.presentation.overviewView to javafx.fxml;
    exports de.dhbw.plugin.presentation.addPropertyView to javafx.graphics;
    opens de.dhbw.plugin.presentation.addPropertyView to javafx.fxml;
    exports de.dhbw.plugin.presentation.addApartmentView to javafx.graphics;
    opens de.dhbw.plugin.presentation.addApartmentView to javafx.fxml;
    exports de.dhbw.plugin.presentation.addComplexView to javafx.graphics;
    opens de.dhbw.plugin.presentation.addComplexView to javafx.fxml;
}