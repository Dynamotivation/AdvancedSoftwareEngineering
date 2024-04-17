package de.dhbw.plugin.presentation.addPropertyView;

import de.dhbw.plugin.presentation.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddPropertyController {
    private MainApp mainApp;

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
    private Spinner<Integer> sizeSpinner;

    @FXML
    private Spinner<Integer> maxTenantsSpinner;

    @FXML
    private Button createButton;

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
        tooltips.add(new Tooltip("Geben Sie eine gültige Größe in Quadratmetern ein (größer als 0)"));
        tooltips.add(new Tooltip("Geben Sie die maximale Anzahl von Mietern ein (größer als 0)"));

        for (Tooltip tooltip : tooltips) {
            tooltip.setShowDelay(javafx.util.Duration.millis(50));
        }

        Tooltip.install(streetNameField, tooltips.get(0));
        Tooltip.install(houseNumberField, tooltips.get(1));
        Tooltip.install(postalCodeField, tooltips.get(2));
        Tooltip.install(cityField, tooltips.get(3));
        Tooltip.install(dateOfConstructionPicker, tooltips.get(4));
        Tooltip.install(sizeSpinner, tooltips.get(5));
        Tooltip.install(maxTenantsSpinner, tooltips.get(6));
    }

    private void addFieldValidationListeners() {
        streetNameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        houseNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        postalCodeField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cityField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        dateOfConstructionPicker.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        sizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        maxTenantsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
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
                dateOfConstructionPicker.getValue() != null &&
                sizeSpinner.getValue() > 0 &&
                maxTenantsSpinner.getValue() > 0;
    }

    @FXML
    private void handleCreateRentalProperty() {
        try {
            if (isInputValid()) {
                String streetName = streetNameField.getText();
                String houseNumber = houseNumberField.getText();
                String postalCode = postalCodeField.getText();
                String city = cityField.getText();
                LocalDate dateOfConstruction = dateOfConstructionPicker.getValue();
                BigDecimal size = BigDecimal.valueOf(sizeSpinner.getValue());
                int maxTenants = maxTenantsSpinner.getValue();

                MainApp.getRentalManagementService().createRentalProperty(streetName, houseNumber, postalCode, city, dateOfConstruction, size, maxTenants);
                mainApp.showOverviewView();
            }
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Erstellen des Mietobjekts");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleShowOverview() {
        mainApp.showOverviewView();
    }
}