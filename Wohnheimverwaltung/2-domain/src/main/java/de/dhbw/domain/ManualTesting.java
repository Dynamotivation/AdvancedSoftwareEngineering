package de.dhbw.domain;

import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.RentCharge;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.SizeUnit;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManualTesting {
    public static void main(String[] args) {
        System.out.println("Starting manual testing...");


        System.out.println("Creating 2 tenants...");
        Tenant tenant1 = new Tenant("John", "Doe", new ContactAvenueEmail("john@doemail.com"));
        Tenant tenant2 = new Tenant("Jane", "Doe", new ContactAvenueEmail("jane@doemail.com"));
        Tenant tenant3 = new Tenant("Alice", "Wonderland", new ContactAvenueEmail("alice@wonderland.de"));
        Tenant tenant4 = new Tenant("Bob", "Builder", new ContactAvenueEmail("bob@builders.com.uk"));


        System.out.println("Creating a RentalProperty...");
        RentalProperty rentalProperty = new RentalProperty("Main Street", "1", "12345", "Springfield", LocalDate.of(2000, 1, 1), Size.squareMeters(new BigDecimal(200)), 2);


        System.out.println("Trying to overfill the rental property...");
        try {
            rentalProperty.rentToTenants(new ArrayList<>() {{
                add(tenant1);
                add(tenant2);
                add(tenant3);
                add(tenant4);
            }}, LocalDate.of(2021, 1, 1), new Rent(100), 1);
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }


        System.out.println("Moving in just John and Jane...");
        rentalProperty.rentToTenants(new ArrayList<>() {{
            add(tenant1);
            add(tenant2);
        }}, LocalDate.of(2021, 1, 1), new Rent(100), 31);


        System.out.println("Trying to double book the rental property...");
        try {
            rentalProperty.rentToTenants(new ArrayList<>() {{
                add(tenant3);
                add(tenant4);
            }}, LocalDate.of(2021, 1, 1), new Rent(100), 1);
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }


        System.out.println("Displaying all information on the rental property and lease...");
        System.out.printf("Address: %s %s, %s %s\n", rentalProperty.getAddress().getStreetName(), rentalProperty.getAddress().getHouseNumber(), rentalProperty.getAddress().getPostalCode(), rentalProperty.getAddress().getCity());
        System.out.printf("Date of construction: %s\n", rentalProperty.getDateOfConstruction());
        System.out.printf("Size: %s\n", rentalProperty.getSize());
        System.out.printf("%s out of %s tenants\n", rentalProperty.getLeaseAgreement().getTenants().size(), rentalProperty.getMaxTenants());
        for (Tenant tenant : rentalProperty.getLeaseAgreement().getTenants()) {
            System.out.printf("Tenant: %s, %s\n", tenant.getSurname(), tenant.getName());
        }
        System.out.println("Lease agreement ID: " + rentalProperty.getLeaseAgreement().getId().getId());
        System.out.printf("Lease agreement start date: %s\n", rentalProperty.getLeaseAgreement().getInclusiveStartDate());
        System.out.printf("Lease agreement monthly day of payment: %s\n", rentalProperty.getLeaseAgreement().getMonthlyDayOfPayment());
        System.out.printf("Lease agreement end date: %s\n", rentalProperty.getLeaseAgreement().getInclusiveEndDate());


        System.out.println("Charging rent manually...");
        tenant1.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), rentalProperty.getLeaseAgreement().getId()));
        tenant2.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), rentalProperty.getLeaseAgreement().getId()));


        System.out.println("Trying fraudulent transaction...");
        try {
            tenant1.addTransaction(new RentCharge(-100, LocalDate.of(2021, 1, 15), new LeaseAgreementId()));
            System.out.println("Error! This should not have been possible!");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }


        System.out.println("Displaying tenant balance...");
        System.out.printf("Balance: %s\n----------------\nTransactions:\n", tenant1.getBalance());
        for (Transaction transaction : tenant1.getOutstandingBalanceHistory()) {
            System.out.printf("%s | %s\n", transaction.getAmount(), transaction.getDate());
        }
    }
}
