package de.dhbw.plugin.presentation;

import de.dhbw.application.services.ApartmentComplexManagementService;
import de.dhbw.application.services.RentalManagementService;
import de.dhbw.application.services.TenantManagementService;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.plugin.persistence.ApartmentComplexJacksonJsonRepository;
import de.dhbw.plugin.persistence.RentalJacksonJsonRepository;
import de.dhbw.plugin.persistence.TenantJacksonJsonRepository;
import de.dhbw.plugin.presentation.addApartmentView.AddApartmentController;
import de.dhbw.plugin.presentation.addComplexView.AddComplexController;
import de.dhbw.plugin.presentation.addPropertyView.AddPropertyController;
import de.dhbw.plugin.presentation.homeView.HomeController;
import de.dhbw.plugin.presentation.overviewView.OverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainApp extends Application {
    private static RentalManagementService rentalManagementService;
    private static TenantManagementService tenantManagementService;
    private static ApartmentComplexManagementService apartmentComplexManagementService;

    private Stage primaryStage;

    private double stageWidth = 800; // Initial width
    private double stageHeight = 600; // Initial height

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

    public void showOverviewView() {
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

    public void showAddApartmentView() {
        try {
            FXMLLoader addApartmentLoader = new FXMLLoader(MainApp.class.getResource("addApartmentView/add-apartment-view.fxml"));
            Parent addApartmentRoot = addApartmentLoader.load();
            AddApartmentController addApartmentController = addApartmentLoader.getController();
            addApartmentController.setMainApp(this);
            Scene addApartmentScene = new Scene(addApartmentRoot);

            primaryStage.setTitle("Neue Mietwohnung hinzufügen");
            primaryStage.setScene(addApartmentScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddComplexView() {
        try {
            FXMLLoader addComplexLoader = new FXMLLoader(MainApp.class.getResource("addComplexView/add-complex-view.fxml"));
            Parent addComplexRoot = addComplexLoader.load();
            AddComplexController addComplexController = addComplexLoader.getController();
            addComplexController.setMainApp(this);
            Scene addComplexScene = new Scene(addComplexRoot);

            primaryStage.setTitle("Neues Wohnheim hinzufügen");
            primaryStage.setScene(addComplexScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAndQuit() {
        try {
            System.out.println("Deleting existing saves...");
            Files.deleteIfExists(new File("tenant.save").toPath());
            Files.deleteIfExists(new File("rental.save").toPath());
            Files.deleteIfExists(new File("complex.save").toPath());

            System.out.println("Saving all data...");
            rentalManagementService.saveAllRentals();
            apartmentComplexManagementService.saveAllOrphanApartmentComplexes();
            //tenantManagementService.saveAllOrphanTenants();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}