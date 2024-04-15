package de.dhbw.plugins.presentation;

import de.dhbw.application.services.ApartmentComplexManagementService;
import de.dhbw.application.services.RentalManagementService;
import de.dhbw.application.services.TenantManagementService;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.plugin.persistence.ApartmentComplexJacksonJsonRepository;
import de.dhbw.plugin.persistence.RentalJacksonJsonRepository;
import de.dhbw.plugin.persistence.TenantJacksonJsonRepository;
import de.dhbw.plugins.presentation.addPropertyView.AddPropertyController;
import de.dhbw.plugins.presentation.overviewView.OverviewController;
import de.dhbw.plugins.presentation.startView.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MainApp extends Application {
    private static RentalManagementService rentalManagementService;
    private static TenantManagementService tenantManagementService;
    private static ApartmentComplexManagementService apartmentComplexManagementService;

    private Stage primaryStage;

    private double stageWidth = 800; // Initial width
    private double stageHeight = 600; // Initial height

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        FXMLLoader homeLoader = new FXMLLoader(MainApp.class.getResource("homeView/home-view.fxml"));
        Parent homeRoot = homeLoader.load();
        HomeController homeController = homeLoader.getController();
        homeController.setMainApp(this);
        Scene homeScene = new Scene(homeRoot);

        primaryStage.setTitle("Wohnheimverwaltung");
        primaryStage.setScene(homeScene);
        primaryStage.setWidth(stageWidth);
        primaryStage.setHeight(stageHeight);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void showNewView() {
        try {
            FXMLLoader overviewLoader = new FXMLLoader(MainApp.class.getResource("overviewView/overview-view.fxml"));
            Parent overviewRoot = overviewLoader.load();
            OverviewController overviewController = overviewLoader.getController();
            overviewController.setMainApp(this);
            Scene overviewScene = new Scene(overviewRoot);

            primaryStage.setTitle("Übersicht");
            primaryStage.setScene(overviewScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create repositories
        RentalRepository rentalRepository = new RentalJacksonJsonRepository();
        TenantRepository tenantRepository = new TenantJacksonJsonRepository();
        ApartmentComplexRepository apartmentComplexRepository = new ApartmentComplexJacksonJsonRepository();

        // Inject Repositories into Application Layer
        rentalManagementService = new RentalManagementService(rentalRepository, tenantRepository, apartmentComplexRepository);
        tenantManagementService = new TenantManagementService(tenantRepository);
        apartmentComplexManagementService = new ApartmentComplexManagementService(apartmentComplexRepository);

        launch();
    }

    public static RentalManagementService getRentalManagementService() {
        return rentalManagementService;
    }

    public static TenantManagementService getTenantManagementService() {
        return tenantManagementService;
    }

    public static ApartmentComplexManagementService getApartmentComplexManagementService() {
        return apartmentComplexManagementService;
    }

    public void showAddApartmentView() {

    }

    public void showAddPropertyView() {
        try {
            FXMLLoader addPropertyLoader = new FXMLLoader(MainApp.class.getResource("addPropertyView/add-property-view.fxml"));
            Parent addPropertyRoot = addPropertyLoader.load();
            AddPropertyController addPropertyController = addPropertyLoader.getController();
            addPropertyController.setMainApp(this);
            Scene addPropertyScene = new Scene(addPropertyRoot);

            primaryStage.setTitle("Neues Mietwohnhaus hinzufügen");
            primaryStage.setScene(addPropertyScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}