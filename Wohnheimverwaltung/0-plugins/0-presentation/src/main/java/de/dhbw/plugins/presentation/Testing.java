package de.dhbw.plugins.presentation;

import de.dhbw.application.services.RentalManagementService;
import de.dhbw.application.services.TenantManagementService;
import de.dhbw.application.snapshotObjects.LeaseAgreementSnapshotDTO;
import de.dhbw.application.snapshotObjects.RentalPropertySnapshotDTO;
import de.dhbw.application.snapshotObjects.TenantSnapshotDTO;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.SizeUnit;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.plugin.persistence.ApartmentComplexJacksonJsonRepository;
import de.dhbw.plugin.persistence.RentalJacksonJsonRepository;
import de.dhbw.plugin.persistence.TenantJacksonJsonRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Testing {
    public static void main(String[] args) {
        // Create repositories
        RentalJacksonJsonRepository rentalJacksonJsonRepository = new RentalJacksonJsonRepository();
        TenantJacksonJsonRepository tenantJacksonJsonRepository = new TenantJacksonJsonRepository();
        ApartmentComplexJacksonJsonRepository apartmentComplexJacksonJsonRepository = new ApartmentComplexJacksonJsonRepository();

        // Inject Repositories into Application Layer
        RentalManagementService rentalManagementService = new RentalManagementService(rentalJacksonJsonRepository, tenantJacksonJsonRepository, apartmentComplexJacksonJsonRepository);
        TenantManagementService tenantManagementService = new TenantManagementService(tenantJacksonJsonRepository);

        System.out.println("Starting manual testing...");


        System.out.println("Creating 4 tenants...");
        var tenantId1 = tenantManagementService.createTenant("John", "Doe", new ContactAvenueEmail("john@doemail.com"));
        var tenantId2 = tenantManagementService.createTenant("Jane", "Doe", new ContactAvenueEmail("jane@doemail.com"));
        var tenantId3 = tenantManagementService.createTenant("Alice", "Wonderland", new ContactAvenueEmail("alice@wonderland.de"));
        var tenantId4 = tenantManagementService.createTenant("Bob", "Builder", new ContactAvenueEmail("bob@builders.com.uk"));
        TenantSnapshotDTO tenant1 = tenantManagementService.getTenantSnapshotById(tenantId1);
        TenantSnapshotDTO tenant2 = tenantManagementService.getTenantSnapshotById(tenantId2);
        TenantSnapshotDTO tenant3 = tenantManagementService.getTenantSnapshotById(tenantId3);
        TenantSnapshotDTO tenant4 = tenantManagementService.getTenantSnapshotById(tenantId4);


        System.out.println("Creating a RentalProperty...");
        RentalId rentalId = rentalManagementService.createRentalProperty("Main Street", "1", "12345", "Springfield", LocalDate.of(2000, 1, 1), new BigDecimal(200), 2);
        RentalPropertySnapshotDTO rentalProperty = rentalManagementService.getRentalPropertySnapshotById(rentalId);


        System.out.println("Trying to overfill the rental property...");
        try {
            rentalManagementService.rentRentalPropertyToTenants(rentalProperty.getId(), new ArrayList<>() {{
                add(tenant1.getId());
                add(tenant2.getId());
                add(tenant3.getId());
                add(tenant4.getId());
            }}, LocalDate.of(2021, 1, 1), new Rent(100), 1);
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }


        System.out.println("Moving in just John and Jane...");
        rentalManagementService.rentRentalPropertyToTenants(rentalProperty.getId(), new ArrayList<>() {{
            add(tenant1.getId());
            add(tenant2.getId());
        }}, LocalDate.of(2021, 1, 1), new Rent(100), 31);


        System.out.println("Trying to double book the rental property...");
        try {
            rentalManagementService.rentRentalPropertyToTenants(rentalProperty.getId(), new ArrayList<>() {{
                add(tenant3.getId());
                add(tenant4.getId());
            }}, LocalDate.of(2021, 1, 1), new Rent(100), 1);
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }


        System.out.println("Refreshing rental data...");
        rentalProperty = rentalManagementService.getRentalPropertySnapshotById(rentalProperty.getId());


        System.out.println("Displaying all information on the rental property and lease...");
        System.out.printf("Address: %s %s, %s %s\n", rentalProperty.getAddress().getStreetName(), rentalProperty.getAddress().getHouseNumber(), rentalProperty.getAddress().getPostalCode(), rentalProperty.getAddress().getCity());
        System.out.printf("Date of construction: %s\n", rentalProperty.getDateOfConstruction());
        System.out.printf("Size: %s\n", rentalProperty.getSize().getValueInPreferredUnit(SizeUnit.SQUARE_METERS));
        System.out.printf("%s out of %s tenants\n", Optional.ofNullable(rentalProperty.getLeaseAgreement())
                .map(LeaseAgreementSnapshotDTO::getTenants)
                .map(List::size)
                .orElse(0), rentalProperty.getMaxTenants());
        for (TenantSnapshotDTO tenant : Optional.ofNullable(rentalProperty.getLeaseAgreement()).map(LeaseAgreementSnapshotDTO::getTenants).orElse(new ArrayList<>())) {
            System.out.printf("Tenant: %s, %s\n", tenant.getName().getSurname(), tenant.getName().getName());
        }
        try {
            System.out.println("Lease agreement ID: " + rentalProperty.getLeaseAgreement().getId().getId());
            System.out.printf("Lease agreement start date: %s\n", rentalProperty.getLeaseAgreement().getInclusiveStartDate());
            System.out.printf("Lease agreement monthly day of payment: %s\n", rentalProperty.getLeaseAgreement().getMonthlyDayOfPayment());
            System.out.printf("Lease agreement end date: %s\n", rentalProperty.getLeaseAgreement().getInclusiveEndDate());
        }
        catch (NullPointerException e) {
            System.out.println("No lease agreement found.");
        }


        /*System.out.println("Charging rent manually...");
        tenant1.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), rentalProperty.getLeaseAgreement().getId()));
        tenant2.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), rentalProperty.getLeaseAgreement().getId()));


        System.out.println("Trying fraudulent transaction...");
        try {
            tenant1.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), new LeaseAgreementId()));
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }*/


        System.out.println("Displaying tenant balance...");
        System.out.printf("Balance: %s\n----------------\nTransactions:\n", tenantManagementService.getOutstandingBalanceOfTenant(tenant1.getId()));
        for (Transaction transaction : tenant1.getOutstandingBalanceHistory()) {
            System.out.printf("%s | %s\n", transaction.getAmount(), transaction.getDate());
        }
    }
}
