package de.dhbw.plugins.presentation;

import de.dhbw.application.services.RentalManagementService;
import de.dhbw.application.services.TenantManagementService;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.plugin.persistence.RentalJacksonJsonRepository;
import de.dhbw.plugin.persistence.TenantJacksonJsonRepository;
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

    private Stage primaryStage;
    private Scene homeScene;
    private Scene overviewScene;

    private double stageWidth = 800; // Initial width
    private double stageHeight = 600; // Initial height

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        // Home Scene
        FXMLLoader homeLoader = new FXMLLoader(MainApp.class.getResource("homeView/home-view.fxml"));
        Parent homeRoot = homeLoader.load();
        HomeController homeController = homeLoader.getController();
        homeController.setMainApp(this);
        homeScene = new Scene(homeRoot);

        // Overview Scene
        FXMLLoader overviewLoader = new FXMLLoader(MainApp.class.getResource("overviewView/overview-view.fxml"));
        Parent overviewRoot = overviewLoader.load();
        OverviewController overviewController = overviewLoader.getController();
        overviewScene = new Scene(overviewRoot);

        // Set home scene as the initial scene
        primaryStage.setTitle("Home Screen");
        primaryStage.setScene(homeScene);
        primaryStage.setWidth(stageWidth);
        primaryStage.setHeight(stageHeight);
        primaryStage.setResizable(true); // Allow window resizing
        primaryStage.show();
    }

    public void showNewView() {
        // Switch to the new scene (NewView.fxml)
        primaryStage.setTitle("New View");
        primaryStage.setScene(overviewScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Create repositories
        RentalRepository rentalRepository = new RentalJacksonJsonRepository();
        TenantRepository tenantRepository = new TenantJacksonJsonRepository();

        // Inject Repositories into Application Layer
        rentalManagementService = new RentalManagementService(rentalRepository, tenantRepository);
        tenantManagementService = new TenantManagementService(tenantRepository);

        // Create rentals
        rentalManagementService.createRentalProperty("Main Street", "1", "12345",
                "Springfield", LocalDate.of(2000, 1, 1), new BigDecimal(200),
                2);

        rentalManagementService.createRentalProperty("Second Street", "2", "54321",
                "Springfield", LocalDate.of(2000, 1, 1), new BigDecimal(200),
                2);

        rentalManagementService.createRentalProperty("Third Street", "3", "54321",
                "Springfield", LocalDate.of(2000, 1, 1), new BigDecimal(200),
                2);

        launch();
    }

    public static RentalManagementService getRentalManagementService() {
        return rentalManagementService;
    }

    public static TenantManagementService getTenantManagementService() {
        return tenantManagementService;
    }
}