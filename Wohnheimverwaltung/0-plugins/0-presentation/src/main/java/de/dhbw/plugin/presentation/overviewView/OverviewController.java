package de.dhbw.plugin.presentation.overviewView;

import com.dukescript.layouts.flexbox.FlexboxLayout;
import com.dukescript.layouts.jfxflexbox.FlexBoxPane;
import de.dhbw.application.snapshotObjects.ApartmentComplexSnapshotDTO;
import de.dhbw.application.snapshotObjects.RentalPropertySnapshotDTO;
import de.dhbw.plugin.presentation.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class OverviewController {
    private MainApp mainApp;

    @FXML
    private VBox apartmentComplexesPane;
    @FXML
    private VBox rentalPropertiesPane;
    @FXML
    private Button addNew;

    @FXML
    public void initialize() {
        var apartmentComplexes = MainApp.getApartmentComplexManagementService().listAllApartmentComplexSnapshots();
        var rentalProperties = MainApp.getRentalManagementService().listAllRentalPropertySnapshots();

        if (rentalProperties.isEmpty() && apartmentComplexes.isEmpty())
            apartmentComplexesPane.getChildren().add(new Label("Keine Mietobjekte vorhanden"));

        if (!apartmentComplexes.isEmpty()) {
            Label label = new Label("Miethäuser:");
            label.setPadding(new Insets(0, 0, 10, 0));
            apartmentComplexesPane.getChildren().add(label);
        }

        for (var apartmentComplex : apartmentComplexes)
            listApartmentUnitsOfComplex(apartmentComplex);

        if (!rentalProperties.isEmpty()) {
            Label label = new Label("Mietwohnhäuser:");
            label.setPadding(new Insets(0, 0, 10, 0));
            rentalPropertiesPane.getChildren().add(label);
        }

        listRentalProperties(rentalProperties);

        createAddButtonDropDown();
    }

    private void createAddButtonDropDown() {
        MenuItem option1 = new MenuItem("Neues Wohnheim");
        MenuItem option2 = new MenuItem("Neue Mietwohnung");
        MenuItem option3 = new MenuItem("Neues Mietwohnhaus");

        option1.setOnAction(e -> showAddComplexView());
        option2.setOnAction(e -> showAddApartmentView());
        option3.setOnAction(e -> showAddRentalPropertyView());

        ContextMenu contextMenu = new ContextMenu(option1, option2, option3);

        addNew.setOnAction(e -> {
            Window window = addNew.getScene().getWindow();
            double screenX = addNew.localToScreen(0, 0).getX();
            double screenY = addNew.localToScreen(0, addNew.getHeight()).getY();

            contextMenu.show(window, screenX, screenY);
        });
    }

    private void listRentalProperties(List<RentalPropertySnapshotDTO> rentalProperties) {
        FlexBoxPane rentalPropertiesGroupPane = new FlexBoxPane();
        rentalPropertiesGroupPane.setJustifyContent(FlexboxLayout.JustifyContent.FLEX_START);
        rentalPropertiesGroupPane.setFlexDirection(FlexboxLayout.FlexDirection.ROW);
        rentalPropertiesGroupPane.setFlexWrap(FlexboxLayout.FlexWrap.WRAP);
        rentalPropertiesGroupPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10),
                new Insets(-5, -5, -10, -5))));

        for (var rentalProperty : rentalProperties) {
            // Create rental unit info pane
            BorderPane rentalInfoPane = new BorderPane();
            rentalInfoPane.setPrefSize(100, 100);
            rentalInfoPane.setTop(new Label(rentalProperty.getAddress().toString().replace(", ", "\n")));
            FontIcon rentalIcon = new FontIcon("gmi-home");
            rentalIcon.setIconSize(32);
            rentalInfoPane.setCenter(rentalIcon);
            rentalInfoPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
            FlexBoxPane.setMargin(rentalInfoPane, new Insets(5));

            // Add to pane
            rentalPropertiesGroupPane.getChildren().add(rentalInfoPane);
        }

        rentalPropertiesPane.getChildren().add(rentalPropertiesGroupPane);
    }

    private void listApartmentUnitsOfComplex(ApartmentComplexSnapshotDTO apartmentComplex) {
        // Style complex info
        BorderPane apartmentUnitInfoPane = new BorderPane();
        apartmentUnitInfoPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        apartmentUnitInfoPane.setMaxHeight(100);
        apartmentUnitInfoPane.setPrefWidth(100);
        BorderPane.setMargin(apartmentUnitInfoPane, new Insets(5, 15, 0, 5));

        // Assemble apartment complex information
        Label complexAddress = new Label(apartmentComplex.getAddress().toString().replace(", ", "\n"));
        apartmentUnitInfoPane.setTop(complexAddress);
        FontIcon complexIcon = new FontIcon("gmi-apartment");
        complexIcon.setIconSize(32);
        apartmentUnitInfoPane.setCenter(complexIcon);

        // Create pane for apartment units
        FlexBoxPane apartmentUnitsPane = new FlexBoxPane();
        apartmentUnitsPane.setJustifyContent(FlexboxLayout.JustifyContent.FLEX_START);
        apartmentUnitsPane.setFlexDirection(FlexboxLayout.FlexDirection.ROW);
        apartmentUnitsPane.setFlexWrap(FlexboxLayout.FlexWrap.WRAP);
        apartmentUnitsPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10),
                new Insets(-5, -5, -10, -5))));


        // Fetching all current apartment units
        var apartments = MainApp.getRentalManagementService()
                .getRentalApartmentUnitSnapshotsByApartmentComplexId(apartmentComplex.getId());

        // Add rentals to pane
        for (var apartment : apartments) {
            // Create rental unit info pane
            BorderPane rentalInfoPane = new BorderPane();
            rentalInfoPane.setPrefSize(100, 100);
            rentalInfoPane.setTop(new Label(apartment.getDoorNumber().toString()));
            FontIcon rentalIcon = new FontIcon("gmi-home-work");
            rentalIcon.setIconSize(32);
            rentalInfoPane.setCenter(rentalIcon);
            rentalInfoPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
            FlexBoxPane.setMargin(rentalInfoPane, new Insets(5));

            // Add to pane
            apartmentUnitsPane.getChildren().add(rentalInfoPane);
        }

        // Create grouping pane
        BorderPane apartmentUnitGroupPane = new BorderPane();
        apartmentUnitGroupPane.setPadding(new Insets(5, 5, 10, 5));
        apartmentUnitGroupPane.setLeft(apartmentUnitInfoPane);
        apartmentUnitGroupPane.setCenter(apartmentUnitsPane);

        // Add grouping pane to complexes pane
        apartmentComplexesPane.getChildren().add(apartmentUnitGroupPane);
    }

    private void showAddApartmentView() {
        mainApp.showAddApartmentView();
    }

    private void showAddRentalPropertyView() {
        mainApp.showAddPropertyView();
    }

    private void showAddComplexView() {
        mainApp.showAddComplexView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void handleSaveAndQuit() {
        mainApp.saveAndQuit();
    }
}