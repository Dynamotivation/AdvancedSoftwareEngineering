package de.dhbw.plugins.presentation.startView;

import de.dhbw.plugins.presentation.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {
    private MainApp mainApp;

    @FXML
    private Button loadButton;

    @FXML
    private Button newButton;

    @FXML
    private void handleLoad(ActionEvent event) {
        System.out.println("Load button clicked");
    }

    @FXML
    private void handleNew(ActionEvent event) {
        System.out.println("New button clicked");
        mainApp.showOverviewView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}