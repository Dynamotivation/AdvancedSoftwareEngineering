package de.dhbw.plugin.presentation.startView;

import de.dhbw.plugin.presentation.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;
import java.nio.file.Files;

public class HomeController {
    private MainApp mainApp;

    @FXML
    private Button loadButton;

    @FXML
    private Button newButton;

    @FXML
    private void handleLoad(ActionEvent event) {
        try {
            if (Files.exists(new File("rental.save").toPath()))
                MainApp.getRentalManagementService().loadRentals();

            if (Files.exists(new File("tenant.save").toPath()))
                MainApp.getTenantManagementService().loadTenants();

            if (Files.exists(new File("complex.save").toPath()))
                MainApp.getApartmentComplexManagementService().loadApartmentComplexes();

            if (Files.notExists(new File("rental.save").toPath()) &&
                    Files.notExists(new File("tenant.save").toPath()) &&
                    Files.notExists(new File("complex.save").toPath())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(String.format("Es wurden keine Daten in %s gefunden.",
                        new File("").getAbsolutePath()));
                alert.show();
            }

            mainApp.showOverviewView();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(String.format("Fehler beim Laden der Daten in %s. Bitte löschen Sie die korrupten Daten.",
                    new File("").getAbsolutePath()));
            alert.setContentText(e.getMessage());
            alert.show();

            // Rollback
            MainApp.getRentalManagementService().clearAllRentals();
            MainApp.getTenantManagementService().clearAllTenants();
            MainApp.getApartmentComplexManagementService().clearAllApartmentComplexes();
        }
    }

    @FXML
    private void handleNew(ActionEvent event) {
        mainApp.showOverviewView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}