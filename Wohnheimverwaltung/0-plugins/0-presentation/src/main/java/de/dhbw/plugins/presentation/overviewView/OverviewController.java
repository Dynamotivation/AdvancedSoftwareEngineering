package de.dhbw.plugins.presentation.overviewView;

import com.dukescript.layouts.flexbox.FlexboxLayout;
import de.dhbw.application.snapshotObjects.RentalPropertySnapshotDTO;
import de.dhbw.plugins.presentation.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import com.dukescript.layouts.jfxflexbox.FlexBoxPane;

public class OverviewController {
    @FXML
    private BorderPane complexesBorderPane;
    @FXML
    private FlexBoxPane test;

    @FXML
    public void initialize() {
        complexesBorderPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        BorderPane borderPane = new BorderPane();
        borderPane.setMaxHeight(100); // Set max height
        borderPane.setPrefWidth(100); // Set preferred width
        BorderPane.setMargin(borderPane, new Insets(5, 15, 0, 5)); // Set margin
        Label label = new Label("Filter");
        borderPane.setTop(label); // Set label as the top node
        FontIcon fontIcon = new FontIcon("gmi-home-work");
        fontIcon.setIconSize(32); // Set icon size
        StackPane iconPane = new StackPane(fontIcon); // Place icon in a StackPane for centering
        borderPane.setCenter(iconPane); // Set iconPane as the center node
        complexesBorderPane.setLeft(borderPane); // Set borderPane as the left node





        complexesBorderPane.setPadding(new Insets(5, 5, 10, 5));

        // Fetching all current rentals
        var Rentals = MainApp.getRentalManagementService().listAllRentalPropertySnapshots();
        System.out.println(Rentals.size());


        // Group by apartment complex
        FlexBoxPane flexBoxPane = new FlexBoxPane();
        flexBoxPane.setJustifyContent(FlexboxLayout.JustifyContent.FLEX_START);
        flexBoxPane.setFlexDirection(FlexboxLayout.FlexDirection.ROW);
        flexBoxPane.setFlexWrap(FlexboxLayout.FlexWrap.WRAP);
        flexBoxPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10),
                new Insets(-5, -5, -10, -5))));
        complexesBorderPane.setCenter(flexBoxPane);

        // Add rentals
        for (RentalPropertySnapshotDTO rental : Rentals) {
            BorderPane rentalBorderPane = new BorderPane();
            rentalBorderPane.setPrefSize(100, 100);

            rentalBorderPane.setTop(new Label(rental.getAddress().toString().replace(", ", "\n")));
            rentalBorderPane.setCenter(new FontIcon("gmi-home-work"));
            rentalBorderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));

            flexBoxPane.getChildren().add(rentalBorderPane);
            FlexBoxPane.setMargin(rentalBorderPane, new Insets(5));

        }

/*



        // Fetching all current rentals
        var Rentals = MainApp.getRentalManagementService().listAllRentalPropertySnapshots();


        // Add rentals
        for (RentalPropertySnapshotDTO rental : Rentals) {
            BorderPane rentalBorderPane = new BorderPane();
            rentalBorderPane.setPrefSize(100, 100);

            // Top is a string
            rentalBorderPane.setTop(new Label(rental.getAddress().toString().replace(", ", "\n")));

            // Centre is a graphic
            rentalBorderPane.setCenter(new FontIcon("gmi-home-work"));

            test.getChildren().add(rentalBorderPane);
        }*/
    }
}