package de.dhbw.plugin.presentation.addComplexView;

import de.dhbw.plugin.presentation.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import java.util.List;

public class AddComplexController {
    @FXML
    private TextField streetNameField;

    @FXML
    private TextField houseNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private DatePicker dateOfConstructionPicker;

    @FXML
    private Button createButton;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        addTooltips();
        addFieldValidationListeners();
    }

    private void addTooltips() {
        List<Tooltip> tooltips = new ArrayList<>();

        tooltips.add(new Tooltip("Geben Sie einen gültigen Straßennamen ein (Buchstaben, Leerzeichen und Sonderzeichen äöüÄÖÜß)"));
        tooltips.add(new Tooltip("Geben Sie eine gültige Hausnummer ein (Zahlen gefolgt von optional einem Buchstaben)"));
        tooltips.add(new Tooltip("Geben Sie eine gültige Postleitzahl ein (genau 5 numerische Ziffern)"));
        tooltips.add(new Tooltip("Geben Sie einen gültigen Stadtnamen ein (Buchstaben, Leerzeichen und Sonderzeichen äöüÄÖÜß)"));
        tooltips.add(new Tooltip("Wählen Sie das Baujahr aus"));

        for (Tooltip tooltip : tooltips) {
            tooltip.setShowDelay(javafx.util.Duration.millis(50));
        }

        Tooltip.install(streetNameField, tooltips.get(0));
        Tooltip.install(houseNumberField, tooltips.get(1));
        Tooltip.install(postalCodeField, tooltips.get(2));
        Tooltip.install(cityField, tooltips.get(3));
        Tooltip.install(dateOfConstructionPicker, tooltips.get(4));
    }

    private void addFieldValidationListeners() {
        streetNameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        houseNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        postalCodeField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cityField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        dateOfConstructionPicker.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    private void validateFields() {
        boolean isValid = isInputValid();
        createButton.setDisable(!isValid);
    }

    private boolean isInputValid() {
        return streetNameField.getText().matches("^[a-zA-ZäöüÄÖÜß\\s]+") &&
                houseNumberField.getText().matches("^[1-9][0-9]*[a-z]?") &&
                postalCodeField.getText().matches("^[0-9]{5}") &&
                cityField.getText().matches("^[a-zA-ZäöüÄÖÜß\\s]+") &&
                dateOfConstructionPicker.getValue() != null;
    }

    @FXML
    private void handleCreateComplex() {
        if (isInputValid()) {
            var streetName = streetNameField.getText();
            var houseNumber = houseNumberField.getText();
            var postalCode = postalCodeField.getText();
            var city = cityField.getText();
            var dateOfConstruction = dateOfConstructionPicker.getValue();

            MainApp.getApartmentComplexManagementService()
                    .createApartmentComplex(streetName, houseNumber, postalCode, city, dateOfConstruction);

            mainApp.showOverviewView();
        }
    }

    public void handleShowOverview() {
        mainApp.showOverviewView();
    }
}