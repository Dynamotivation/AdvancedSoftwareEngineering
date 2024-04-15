package de.dhbw.plugins.presentation.addApartmentView;

import de.dhbw.plugins.presentation.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddApartmentController {
    private MainApp mainApp;

    @FXML
    private ComboBox<String> apartmentComplexComboBox;

    @FXML
    private Spinner<Integer> apartmentNumberSpinner;

    @FXML
    private Spinner<Integer> floorSpinner;

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

        var apartmentComplexes = MainApp.getApartmentComplexManagementService().listAllApartmentComplexSnapshots();
        ObservableList<String> apartmentComplexAddresses = FXCollections.observableArrayList();

        for (var apartmentComplex : apartmentComplexes)
            apartmentComplexAddresses.add(String.format("%s %s, %s %s",
                    apartmentComplex.getAddress().getStreetName(), apartmentComplex.getAddress().getHouseNumber(),
                    apartmentComplex.getAddress().getPostalCode(), apartmentComplex.getAddress().getCity()));

        apartmentComplexComboBox.setItems(apartmentComplexAddresses);
    }

    private void addTooltips() {
        List<Tooltip> tooltips = new ArrayList<>();

        tooltips.add(new Tooltip("Wählen Sie ein gültiges Wohnheim aus"));
        tooltips.add(new Tooltip("Geben Sie eine gültige Wohnungsnummer ein (größer als 0)"));
        tooltips.add(new Tooltip("Geben Sie eine gültige Etage ein (größer als 0)"));
        tooltips.add(new Tooltip("Geben Sie eine gültige Größe in Quadratmetern ein (größer als 0)"));
        tooltips.add(new Tooltip("Geben Sie die maximale Anzahl von Mietern ein (größer als 0)"));

        for (Tooltip tooltip : tooltips) {
            tooltip.setShowDelay(javafx.util.Duration.millis(50));
        }

        Tooltip.install(apartmentComplexComboBox, tooltips.get(0));
        Tooltip.install(apartmentNumberSpinner, tooltips.get(1));
        Tooltip.install(floorSpinner, tooltips.get(2));
        Tooltip.install(sizeSpinner, tooltips.get(3));
        Tooltip.install(maxTenantsSpinner, tooltips.get(4));
    }

    private void addFieldValidationListeners() {
        apartmentComplexComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        apartmentNumberSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        floorSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        sizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        maxTenantsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    private void validateFields() {
        boolean isValid = isInputValid();
        createButton.setDisable(!isValid);
    }

    private boolean isInputValid() {
        return apartmentComplexComboBox.getValue() != null
                && apartmentNumberSpinner.getValue() != null
                && floorSpinner.getValue() != null
                && sizeSpinner.getValue() != null
                && maxTenantsSpinner.getValue() != null;
    }

    @FXML
    private void handleCreateApartment() {
        if (isInputValid()) {
            var apartmentComplex = MainApp.getApartmentComplexManagementService()
                    .listAllApartmentComplexSnapshots()
                    .get(apartmentComplexComboBox.getSelectionModel().getSelectedIndex())
                    .getId();
            var apartmentNumber = apartmentNumberSpinner.getValue();
            var floor = floorSpinner.getValue();
            var size = new BigDecimal(sizeSpinner.getValue());
            var maxTenants = maxTenantsSpinner.getValue();

            MainApp.getRentalManagementService()
                    .createRentalApartmentUnit(apartmentComplex, apartmentNumber, floor, size, maxTenants);

            mainApp.showOverviewView();
        }
    }

    public void showAddComplexView() {
        mainApp.showAddComplexView();
    }

    public void handleShowOverview() {
        mainApp.showOverviewView();
    }
}